package mozay.test_assessment.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoadBoxRequest {
    @NotNull(message = "Box ID is required")
    private Long boxId;

    @NotEmpty(message = "At least one item is required")
    private List<ItemRequest> items;
}
