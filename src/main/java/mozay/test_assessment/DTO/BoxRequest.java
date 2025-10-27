package mozay.test_assessment.DTO;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoxRequest {
    @NotBlank( message = "Box txref is required")
    @Size(max=20, message= "Txref must not exceed 20 characters")
    private String txref;

    @NotNull(message = "Weight limit is required")
    @Max(value = 500, message = "Weight limit cannot exceed 500 grams")
    @Positive(message = "Weight limit must be greater than zero")
    private int weightLimit;

    @NotNull(message = "Battery capacity is required")
    @Min(value = 0, message = "Battery capacity must be between 0 and 100")
    @Max(value = 100, message = "Battery capacity must be between 0 and 100")
    private int batteryCapacity;

}
