package mozay.test_assessment;

import mozay.test_assessment.DTO.BoxRequest;
import mozay.test_assessment.DTO.BoxResponse;
import mozay.test_assessment.DTO.ItemRequest;
import mozay.test_assessment.DTO.ItemResponse;



import java.util.List;

public interface BoxService {

    BoxResponse createBox(BoxRequest request);
    BoxResponse loadBox(String txRef, List<ItemRequest> items);
    List<ItemResponse> getLoadedItems(String txRef);
    List<BoxResponse> getAvailableBoxes();
    int getBatteryLevel(String txRef);
}

