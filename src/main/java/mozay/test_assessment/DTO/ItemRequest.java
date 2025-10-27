package mozay.test_assessment.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequest {

    @NotBlank(message = "Item name is required")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$",
            message = "Name must contain only letters, numbers, hyphen and underscore")
    private String name;

    @NotNull(message = "Weight is required")
    @Positive(message = "Weight must be greater than zero")
    private int weight;

    @NotBlank(message = "Item code is required")
    @Pattern(regexp = "^[A-Z0-9_]+$",
            message = "Code must contain only uppercase letters, numbers and underscore")
    private String code;

}
