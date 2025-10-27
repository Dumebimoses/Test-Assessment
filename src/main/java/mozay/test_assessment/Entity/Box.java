package mozay.test_assessment.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mozay.test_assessment.Enums.BoxState;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "boxes")
public class Box {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

 // Create the columns for our table

 @Column(name = "tx_ref", length = 20, unique = true, nullable = false)
 @Size(max = 20, message = "Transaction reference must be 20 characters max")
 @NotBlank(message = "Transaction reference is required")
 private String txref;

 @Column(name = "weight_limit", nullable = false)
 @Max(value = 500, message = "Weight limit cannot exceed 500 grams")
 @NotNull(message = "Weight limit is required")
 private int weightLimit = 500;

 @Column(name = "battery_capacity", nullable = false)
 @Min( value = 0, message= "Battery capacity must be between 0 and 100")
 @Max(value = 100, message = "Battery capacity must be between 0 and 100")
 @NotNull( message = "Battery capacity is required")
 private int batteryCapacity;

 @Enumerated (EnumType.STRING)
 @Column( nullable = false)
 private BoxState state;

 @OneToMany(mappedBy = "box", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items = new ArrayList<>();

 public int getCurrentWeight() {
     return items.stream()
             .mapToInt(Item::getWeight)
             .sum();
 }

    public int getAvailableCapacity() {
        return weightLimit - getCurrentWeight();
 }

 public boolean canLoad(){
     return batteryCapacity >= 25 &&
             (state == BoxState.IDLE || state == BoxState.LOADING);
 }


}
