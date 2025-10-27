package mozay.test_assessment;

import mozay.test_assessment.Entity.Box;
import mozay.test_assessment.Entity.Item;
import mozay.test_assessment.Enums.BoxState;
import mozay.test_assessment.Repository.BoxRepository;
import mozay.test_assessment.Repository.ItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class TestAssessmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestAssessmentApplication.class, args);
    }


}
