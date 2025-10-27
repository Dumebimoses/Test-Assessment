package mozay.test_assessment.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponse {
        private Long id;
        private String name;
        private int weight;
        private String code;
}
