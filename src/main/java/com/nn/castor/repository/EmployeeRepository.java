package com.nn.castor.repository;

import com.nn.castor.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByIdentification (String identification);
}
