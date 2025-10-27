package mozay.test_assessment.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatteryLevelResponse {
    private Long boxId;
    private String txref;
    private Integer batteryCapacity;
    private String status;
}
