package com.nn.castor.service;

import com.nn.castor.domain.Employee;
import com.nn.castor.domain.Position;
import com.nn.castor.exception.EmployeeAlreadyExistsException;
import com.nn.castor.exception.EmployerNotFoundException;
import com.nn.castor.exception.PositionNotFoundException;
import com.nn.castor.repository.EmployeeRepository;
import com.nn.castor.repository.PositionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private PositionRepository positionRepository;

    @Mock
    private MultipartFile photoFile;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveNewEmployee() throws IOException {
        Employee employeeToCreate = new Employee();
        employeeToCreate.setIdentification("12345");
        Position position = new Position();
        position.setId(1L);
        employeeToCreate.setPosition(position);

        given(positionRepository.findById(anyLong())).willReturn(Optional.of(position));
        given(employeeRepository.findByIdentification(anyString())).willReturn(null);
        given(photoFile.getOriginalFilename()).willReturn("photo.jpg");
        InputStream inputStream = new ByteArrayInputStream("test image content".getBytes());
        given(photoFile.getInputStream()).willReturn(inputStream);
        given(employeeRepository.save(any(Employee.class))).willReturn(employeeToCreate);

        Employee savedEmployee = employeeService.saveNewEmployee(employeeToCreate, photoFile);

        assertNotNull(savedEmployee);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testSaveNewEmployeePositionNotFound() {
        Employee employeeToCreate = new Employee();
        Position position = new Position();
        position.setId(1L);
        employeeToCreate.setPosition(position);

        given(positionRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(PositionNotFoundException.class,
                () -> employeeService.saveNewEmployee(employeeToCreate, photoFile)
        );
    }

    @Test
    void testSaveNewEmployeeAlreadyExists() {
        Employee employeeToCreate = new Employee();
        employeeToCreate.setIdentification("12345");
        Position position = new Position();
        position.setId(1L);
        employeeToCreate.setPosition(position);

        given(positionRepository.findById(anyLong())).willReturn(Optional.of(position));
        given(employeeRepository.findByIdentification(anyString())).willReturn(new Employee());

        assertThrows(EmployeeAlreadyExistsException.class,
                () -> employeeService.saveNewEmployee(employeeToCreate, photoFile)
        );
    }

    @Test
    void testUpdateEmployee() {
        Employee employeeToUpdate = new Employee();
        employeeToUpdate.setIdentification("12345");
        employeeToUpdate.setName("John");
        Position position = new Position();
        position.setId(1L);
        employeeToUpdate.setPosition(position);

        Employee existingEmployee = new Employee();
        existingEmployee.setId(1L);

        given(employeeRepository.findById(anyLong())).willReturn(Optional.of(existingEmployee));
        given(employeeRepository.save(any(Employee.class))).willReturn(existingEmployee);

        Employee updatedEmployee = employeeService.updateEmployee(employeeToUpdate, 1L);

        assertNotNull(updatedEmployee);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testUpdateEmployeeNotFound() {
        Employee employeeToUpdate = new Employee();

        given(employeeRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(EmployerNotFoundException.class,
                () -> employeeService.updateEmployee(employeeToUpdate, 1L)
        );
    }

    @Test
    void testDeleteEmployee() {
        Employee employee = new Employee();
        employee.setId(1L);

        given(employeeRepository.findById(anyLong())).willReturn(Optional.of(employee));
        doNothing().when(employeeRepository).delete(any(Employee.class));

        Boolean result = employeeService.deleteEmployee(1L);

        assertTrue(result);
        verify(employeeRepository, times(1)).delete(any(Employee.class));
    }

    @Test
    void testDeleteEmployeeNotFound() {
        given(employeeRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(EmployerNotFoundException.class, () -> employeeService.deleteEmployee(1L));
    }

    @Test
    void testGetEmployeeId() {
        Employee employee = new Employee();
        employee.setId(1L);

        given(employeeRepository.findById(anyLong())).willReturn(Optional.of(employee));

        Optional<Employee> result = employeeService.getEmployeeId(1L);

        assertTrue(result.isPresent());
        assertEquals(employee.getId(), result.get().getId());
    }

    @Test
    void testGetEmployeeIdNotFound() {
        given(employeeRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(EmployerNotFoundException.class, () -> employeeService.getEmployeeId(1L));
    }

    @Test
    void testGetAllEmployees() {
        Employee employee = new Employee();
        List<Employee> employees = List.of(employee);

        given(employeeRepository.findAll()).willReturn(employees);

        Optional<List<Employee>> result = employeeService.getAllEmployees();

        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
    }
}
