package mozay.test_assessment.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mozay.test_assessment.Entity.Box;

@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Item name is required")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$",
            message = "Name must contain only letters, numbers, hyphen and underscore")
    @Column(nullable = false)
    private String name;

    @Positive(message = "Weight must be greater than zero")
    @Column(nullable = false)
    private int weight;

    @NotBlank(message = "Item code is required")
    @Pattern(regexp = "^[A-Z0-9_]+$",
            message = "Code must contain only uppercase letters, numbers and underscore")
    @Column(nullable = false, unique = true)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "box_id")
    private Box box;

    public Item(String name, int weight, String code) {
        this.name = name;
        this.weight = weight;
        this.code = code;
    }
}