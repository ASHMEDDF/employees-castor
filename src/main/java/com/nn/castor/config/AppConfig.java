package com.nn.castor.config;

import com.nn.castor.domain.Position;
import com.nn.castor.repository.EmployeeRepository;
import com.nn.castor.repository.PositionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class AppConfig {

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository employeeRepository, PositionRepository positionRepository) {
        return args -> {

            Position scrumMaster = new Position(1L, "Scrum Master");
            Position developer = new Position(2L, "Developer");
            Position qa = new Position(3L, "QA");
            Position po = new Position(4L, "PO");

            positionRepository.saveAll(Arrays.asList(scrumMaster, developer, qa, po));
        };
    }
}
