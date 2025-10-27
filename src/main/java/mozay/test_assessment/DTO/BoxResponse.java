package mozay.test_assessment.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mozay.test_assessment.Enums.BoxState;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BoxResponse {
    private String txref;
    private int weightLimit;
    private int batteryCapacity;
    private BoxState state;
    private int currentWeight;
    private List<ItemResponse> items;

}
