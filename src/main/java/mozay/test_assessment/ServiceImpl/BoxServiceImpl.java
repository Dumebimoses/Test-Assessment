package mozay.test_assessment.ServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mozay.test_assessment.BoxService;
import mozay.test_assessment.DTO.BoxRequest;
import mozay.test_assessment.DTO.BoxResponse;
import mozay.test_assessment.DTO.ItemRequest;
import mozay.test_assessment.DTO.ItemResponse;
import mozay.test_assessment.Entity.Box;
import mozay.test_assessment.Entity.Item;
import mozay.test_assessment.Enums.BoxState;
import mozay.test_assessment.Exception.BoxNotFoundException;
import mozay.test_assessment.Exception.InsufficientBatteryException;
import mozay.test_assessment.Exception.WeightExceededException;
import mozay.test_assessment.Repository.BoxRepository;
import mozay.test_assessment.Repository.ItemRepository;
import mozay.test_assessment.Exception.BadRequestException;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor

public class BoxServiceImpl implements BoxService {

    private final BoxRepository boxRepository;
    private final ItemRepository itemRepository;


    public BoxResponse createBox(BoxRequest request) throws BadRequestException{
        // if box exists by txRef, error
        Optional<Box> existing =  boxRepository.findByTxref(request.getTxref());
        if(existing.isPresent()){
            throw new BadRequestException("Box with txRef already exists");
        }
        Box box = new Box();
        box.setTxref(request.getTxref());
        box.setWeightLimit(request.getWeightLimit());
        box.setBatteryCapacity(request.getBatteryCapacity());
        box.setState(BoxState.IDLE);

        Box savedBox =  boxRepository.save(box);
        log.info("Box with txRef {} has been created", savedBox.getTxref());
        return new BoxResponse(
                savedBox.getTxref(),
                savedBox.getWeightLimit(),
                savedBox.getBatteryCapacity(),
                savedBox.getState(),
                savedBox.getCurrentWeight(),
                new ArrayList<>()
        );

    }

    @Transactional
    public BoxResponse loadBox(String txRef, List<ItemRequest> itemRequests){
        Box box = boxRepository.findByTxref(txRef)
                .orElseThrow(() -> new BoxNotFoundException("Box with txRef " + txRef + " not found"));

        //check battery level

        if (box.getBatteryCapacity() < 25){
            throw new InsufficientBatteryException(
                    "Box battery level is below 25%. Current: " + box.getBatteryCapacity() + "%");
        }
        // check if box can load
        if (box.getState() != BoxState.IDLE && box.getState() != BoxState.LOADING) {
            throw new IllegalStateException("Box is not available for loading. Current state: " + box.getState());
        }

        // Calculate total weight of new items
        int totalNewWeight = itemRequests.stream()
                .mapToInt(ItemRequest::getWeight)
                .sum();

        // Check weight limit
        if (box.getCurrentWeight() + totalNewWeight > box.getWeightLimit()) {
            throw new WeightExceededException(
                    "Total weight exceeds box capacity. Available: " + box.getAvailableCapacity() +
                            "gr, Requested: " + totalNewWeight + "gr");
        }

        box.setState(BoxState.LOADING);


        for (ItemRequest itemRequest : itemRequests) {
            Item item = new Item();
            item.setName(itemRequest.getName());
            item.setWeight(itemRequest.getWeight());
            item.setCode(itemRequest.getCode());
            item.setBox(box);
            box.getItems().add(item);
        }

        box.setState(BoxState.LOADED);

        Box savedBox = boxRepository.save(box);
        log.info("Loaded {} items into box: {}", itemRequests.size(), txRef);
        return new BoxResponse(
                savedBox.getTxref(),
                savedBox.getWeightLimit(),
                savedBox.getBatteryCapacity(),
                savedBox.getState(),
                savedBox.getCurrentWeight(),
                new ArrayList<>()
        );
    }

    @Transactional(readOnly = true)
    public List<ItemResponse> getLoadedItems(String txref) {
        Box box = boxRepository.findByTxref(txref)
                .orElseThrow(() -> new BoxNotFoundException("Box not found: " + txref));

        return box.getItems().stream()
                .map(this::mapToItemResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BoxResponse> getAvailableBoxes() {
        List<Box> availableBoxes = boxRepository.findByState(BoxState.IDLE);
        return availableBoxes.stream()
                .filter(box -> box.getBatteryCapacity() >= 25) // Optional filter
                .map(box -> new BoxResponse(
                        box.getTxref(),
                        box.getWeightLimit(),
                        box.getBatteryCapacity(),
                        box.getState(),
                        box.getCurrentWeight(),
                        box.getItems().stream()
                                .map(item -> new ItemResponse(item.getId(), item.getName(), item.getWeight(), item.getCode()))
                                .toList()
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public int getBatteryLevel(String txref) {
        Box box = boxRepository.findByTxref(txref)
                .orElseThrow(() -> new BoxNotFoundException("Box not found: " + txref));
        return box.getBatteryCapacity();
    }

    @Transactional(readOnly = true)
    public List<BoxResponse> getAllBoxes() {
        return boxRepository.findAll().stream()
                .map(this::mapToBoxResponse)
                .collect(Collectors.toList());
    }

    private BoxResponse mapToBoxResponse(Box box) {
        return new BoxResponse();
    }

    private ItemResponse mapToItemResponse(Item item) {
        return new ItemResponse(
                item.getId(),
                item.getName(),
                item.getWeight(),
                item.getCode()
        );
    }
}

