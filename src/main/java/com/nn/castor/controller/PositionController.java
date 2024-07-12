package com.nn.castor.controller;

import com.nn.castor.domain.Employee;
import com.nn.castor.domain.Position;
import com.nn.castor.dto.ResponseEmployeeCreatedDto;
import com.nn.castor.exception.EmployeeAlreadyExistsException;
import com.nn.castor.exception.EmployerNotFoundException;
import com.nn.castor.exception.PositionNotFoundException;
import com.nn.castor.repository.EmployeeRepository;
import com.nn.castor.repository.PositionRepository;
import com.nn.castor.service.EmployeeService;
import com.nn.castor.service.PositionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/position")
@AllArgsConstructor
public class PositionController {

//    @Autowired
//    private PositionRepository positionRepository;
//
//    private PositionService positionService;
//
//    // GET all employees
//    @GetMapping
//    public Optional<List<Position>> getAllEmployees() {
//        return positionRepository.findAll();
//    }
//
//    // GET a single employee by id
//    @GetMapping("/{id}")
//    public ResponseEntity<Optional<Employee>> getEmployeeById(@PathVariable(value = "id") Long employeeId) {
//        try {
//            return ResponseEntity.ok().body(employeeService.getEmployeeId(employeeId));
//        } catch (EmployerNotFoundException e) {
//            return ResponseEntity.status(409).build();
//        } catch (Exception e) {
//            return  ResponseEntity.internalServerError().build();
//        }
//
//    }
//
//    // CREATE a new employee
//    @PostMapping
//    public ResponseEntity<ResponseEmployeeCreatedDto> createEmployee(@NotNull @Valid @RequestBody Employee employee) {
//
//        try {
//            Employee savedEmployee = employeeService.saveNewEmployee(employee);
//            ResponseEmployeeCreatedDto response = new ResponseEmployeeCreatedDto(
//                    savedEmployee.getId(),
//                    savedEmployee.getNationalId(),
//                    savedEmployee.getName(),
//                    savedEmployee.getPhoto(),
//                    savedEmployee.getDateEntry(),
//                    savedEmployee.getPosition()
//            );
//
//            return new ResponseEntity<>(response, HttpStatus.CREATED);
//        } catch (PositionNotFoundException e) {
//            return ResponseEntity.notFound().build();
//        } catch (EmployeeAlreadyExistsException e) {
//            return ResponseEntity.status(409).build();
//        } catch (Exception e) {
//            return  ResponseEntity.internalServerError().build();
//        }
//    }
//
//    // UPDATE an existing employee
//    @PutMapping("/{id}")
//    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
//                                                   @Valid @RequestBody Employee employeeDetails) {
//
//        try {
//            Employee updatedEmployee = employeeService.updateEmployee(employeeDetails, employeeId);
//            return ResponseEntity.ok(updatedEmployee);
//        } catch (EmployerNotFoundException e) {
//            return ResponseEntity.notFound().build();
//        } catch (Exception e) {
//            return  ResponseEntity.internalServerError().build();
//        }
//    }
//
//    // DELETE an employee
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteEmployee(@PathVariable(value = "id") Long employeeId) {
//
//        try {
//            if (Boolean.TRUE.equals(employeeService.deleteEmployee(employeeId))){
//                return ResponseEntity.ok().build();
//            }
//            return ResponseEntity.notFound().build();
//        } catch (EmployerNotFoundException e) {
//            return ResponseEntity.notFound().build();
//        } catch (Exception e) {
//            return  ResponseEntity.internalServerError().build();
//        }
//
//    }
}
