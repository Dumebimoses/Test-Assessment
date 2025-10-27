package mozay.test_assessment.Repository;

import mozay.test_assessment.Entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Long> {


}
