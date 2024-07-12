package com.nn.castor.controller;

import com.nn.castor.domain.Employee;
import com.nn.castor.domain.Position;
import com.nn.castor.dto.ResponseEmployeeCreatedDto;
import com.nn.castor.service.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class EmployeeControllerTests {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @Test
    void testGetAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(
                1L,
                "John Doe",
                "123456789",
                "john.jpg",
                new Date(),
                new Position(
                        1L,
                        "scum Master")));
        employees.add(new Employee(
                2L,
                "Jane Smith",
                "987654321",
                "jane.jpg",
                new Date(),
                new Position(
                        1L,
                        "scum Master")));
        when(employeeService.getAllEmployees()).thenReturn(Optional.of(employees));

        // Calling the controller method
        Optional<List<Employee>> result = employeeController.getAllEmployees();

        // Assertions
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(2, result.get().size());
    }

    @Test
    void testGetEmployeeById() {
        Employee employee = new Employee(
                1L,
                "John Doe",
                "123456789",
                "john.jpg",
                new Date(),
                new Position(
                        1L,
                        "scum Master"));
        when(employeeService.getEmployeeId(1L)).thenReturn(Optional.of(employee));

        // Calling the controller method
        ResponseEntity<Optional<Employee>> responseEntity = employeeController.getEmployeeById(1L);

        // Assertions
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(Objects.requireNonNull(responseEntity.getBody()).isPresent());
        Assertions.assertEquals(employee, responseEntity.getBody().get());
    }

    @Test
    void testCreateEmployee_Success() {
        // Mocking the behavior of EmployeeService
        Employee employee = new Employee(
                1L,
                "John Doe",
                "123456789",
                "john.jpg",
                new Date(),
                new Position(
                        1L,
                        "scum Master"));
        when(employeeService.saveNewEmployee(employee)).thenReturn(employee);

        // Calling the controller method
        ResponseEntity<ResponseEmployeeCreatedDto> responseEntity = employeeController.createEmployee(employee);

        // Assertions
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertTrue(responseEntity.getBody() != null && responseEntity.getBody().getId() == 1L);
    }

    @Test
    void testUpdateEmployee_Success() {
        // Mocking the behavior of EmployeeService
        Employee employee = new Employee(
                1L,
                "John Doe",
                "123456789",
                "john.jpg",
                new Date(),
                new Position(
                        1L,
                        "scum Master"));
        when(employeeService.updateEmployee(employee, 1L)).thenReturn(employee);

        // Calling the controller method
        ResponseEntity<Employee> responseEntity = employeeController.updateEmployee(1L, employee);

        // Assertions
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(responseEntity.getBody() != null && responseEntity.getBody().getId() == 1L);
    }

    @Test
    void testDeleteEmployee_Success() {
        // Mocking the behavior of EmployeeService
        when(employeeService.deleteEmployee(1L)).thenReturn(true);

        // Calling the controller method
        ResponseEntity<Void> responseEntity = employeeController.deleteEmployee(1L);

        // Assertions
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testDeleteEmployee_NotFound() {
        when(employeeService.deleteEmployee(1L)).thenReturn(false);

        // Calling the controller method
        ResponseEntity<Void> responseEntity = employeeController.deleteEmployee(1L);

        // Assertions
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

}
