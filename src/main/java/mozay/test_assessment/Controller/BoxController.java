package mozay.test_assessment.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mozay.test_assessment.BoxService;
import mozay.test_assessment.DTO.BoxRequest;
import mozay.test_assessment.DTO.BoxResponse;
import mozay.test_assessment.DTO.ItemRequest;
import mozay.test_assessment.DTO.ItemResponse;
import mozay.test_assessment.Repository.BoxRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/boxes")
@RequiredArgsConstructor
@Slf4j
public class BoxController {

    private final BoxRepository boxRepository;
    private final BoxService boxService;

    @PostMapping("create")
    public ResponseEntity<BoxResponse> createBox(@Valid @RequestBody BoxRequest request) {
        log.info("REST Request to create box with txref: {}", request.getTxref());

        BoxResponse response = boxService.createBox(request);

        log.info("Successfully created box with ID: {} and txref: {}",
                response.getTxref());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{txref}/items")
    public ResponseEntity<BoxResponse> loadBox(@PathVariable String txref, @Valid @RequestBody List<ItemRequest> items) {

        log.info("REST Request to load {} items into box: {}", items.size(), txref);

        BoxResponse response = boxService.loadBox(txref, items);

        log.info("Successfully loaded {} items into box: {}. Current weight: {}gr",
                items.size(), txref, response.getCurrentWeight());

        return ResponseEntity.ok(response);
    }
    @GetMapping("/{txref}/items")
    public ResponseEntity<List<ItemResponse>> getLoadedItems(@PathVariable String txref) {
        log.info("REST Request to get loaded items for box: {}", txref);

        List<ItemResponse> items = boxService.getLoadedItems(txref);

        log.info("Retrieved {} items for box: {}", items.size(), txref);

        return ResponseEntity.ok(items);
    }

    @GetMapping("/available")
    public ResponseEntity<List<BoxResponse>> getAvailableBoxes() {
        log.info("REST Request to get available boxes");

        List<BoxResponse> boxes = boxService.getAvailableBoxes();

        log.info("Found {} available boxes", boxes.size());

        return ResponseEntity.ok(boxes);
    }

    @GetMapping("/{txref}/battery")
    public ResponseEntity<Integer> getBatteryLevel(@PathVariable String txref) {
        log.info("REST Request to get battery level for box: {}", txref);

        int batteryLevel = boxService.getBatteryLevel(txref);

        log.info("Battery level for box {}: {}%", txref, batteryLevel);

        return ResponseEntity.ok(batteryLevel);
    }

}
