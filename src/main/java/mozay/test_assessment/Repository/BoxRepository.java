package mozay.test_assessment.Repository;

import mozay.test_assessment.Entity.Box;
import mozay.test_assessment.Enums.BoxState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoxRepository extends JpaRepository<Box,Long> {
    Optional<Box> findByTxref(String txref);
    List<Box> findByState(BoxState state);
}
