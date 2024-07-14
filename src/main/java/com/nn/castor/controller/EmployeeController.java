package com.nn.castor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nn.castor.domain.Employee;
import com.nn.castor.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/employees")
@AllArgsConstructor
public class EmployeeController {

    @Autowired
    private final EmployeeService employeeService;
    private final ObjectMapper objectMapper;

    @GetMapping
    public Optional<List<Employee>> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Employee>> getEmployeeById(@PathVariable(value = "id") Long employeeId) {
        return ResponseEntity.ok().body(employeeService.getEmployeeId(employeeId));
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestPart("employee") String employeeJson,
                                                   @RequestPart("photo") MultipartFile photoFile) throws IOException {
        Employee employee = objectMapper.readValue(employeeJson, Employee.class);
        Employee createdEmployee = employeeService.saveNewEmployee(employee, photoFile);
        return ResponseEntity.ok(createdEmployee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
                                                   @Valid @RequestBody Employee employeeDetails) {
        Employee updatedEmployee = employeeService.updateEmployee(employeeDetails, employeeId);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable(value = "id") Long employeeId) {
        if (Boolean.TRUE.equals(employeeService.deleteEmployee(employeeId))) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}