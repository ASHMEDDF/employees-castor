package com.nn.castor.service;

import com.nn.castor.domain.Employee;
import com.nn.castor.domain.Position;
import com.nn.castor.exception.EmployeeAlreadyExistsException;
import com.nn.castor.exception.EmployerNotFoundException;
import com.nn.castor.exception.PositionNotFoundException;
import com.nn.castor.repository.EmployeeRepository;
import com.nn.castor.repository.PositionRepository;
import org.assertj.core.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private PositionRepository positionRepository;

    @InjectMocks
    private EmployeeService employeeService;

    // ## Tests para `saveNewEmployee`

    // **Test para guardar un nuevo empleado exitosamente**
    @Test
    public void saveNewEmployee_whenEmployeeDoesNotExist_thenEmployeeIsSaved() {
        // **Arrange**
        Employee employeeToCreate = new Employee();
        employeeToCreate.setNationalId("12345678");
        // ... set other fields

        Position position = new Position();
        position.setId(1L);
        // ... set other fields

        Employee savedEmployee = new Employee();
        savedEmployee.setId(1L);
        // ... set other fields (same as employeeToCreate)

        Mockito.when(positionRepository.findById(1L)).thenReturn(Optional.of(position));
        Mockito.when(employeeRepository.findByNationalId("12345678")).thenReturn(null);
        Mockito.when(employeeRepository.save(employeeToCreate)).thenReturn(savedEmployee);

        // **Act**
        Employee result = employeeService.saveNewEmployee(employeeToCreate);

        // **Assert**
        // Se verifica que el resultado sea el mismo que el empleado guardado
        Assertions.assertThat(result).isEqualTo(savedEmployee);
        // Se verifica que se haya llamado al método `save` del repositorio con el empleado correcto
        Mockito.verify(employeeRepository).save(employeeToCreate);
    }

    // **Test para guardar un nuevo empleado con un ID de posición inexistente**
    @Test(expected = PositionNotFoundException.class)
    public void saveNewEmployee_whenPositionNotFound_thenThrowsException() {
        // **Arrange**
        Employee employeeToCreate = new Employee();
        employeeToCreate.setNationalId("12345678");
        // ... set other fields

        Mockito.when(positionRepository.findById(1L)).thenReturn(Optional.empty());

        // **Act**
        employeeService.saveNewEmployee(employeeToCreate);

        // **Assert** (verificado por la excepción esperada)
    }

    // **Test para guardar un nuevo empleado con un número de identificación nacional ya existente**
    @Test(expected = EmployeeAlreadyExistsException.class)
    public void saveNewEmployee_whenEmployeeExists_thenThrowsException() {
        // **Arrange**
        Employee employeeToCreate = new Employee();
        employeeToCreate.setNationalId("12345678");
        // ... set other fields

        Mockito.when(employeeRepository.findByNationalId("12345678")).thenReturn(new Employee());

        // **Act**
        employeeService.saveNewEmployee(employeeToCreate);

        // **Assert** (verificado por la excepción esperada)
    }

    // ## Tests para `updateEmployee`

    // **Test para actualizar un empleado exitosamente**
    @Test
    public void updateEmployee_whenEmployeeExists_thenEmployeeIsUpdated() {
        // **Arrange**
        Employee employeeToUpdate = new Employee();
        employeeToUpdate.setId(1L);
        employeeToUpdate.setNationalId("12345678");
        // ... set other fields

        Employee existingEmployee = new Employee();
        existingEmployee.setId(1L);
        // ... set other fields (different from employeeToUpdate)

        Position position = new Position();
        position.setId(1L);
        // ... set other fields

        Employee updatedEmployee = new Employee();
        updatedEmployee.setId(1L);
        // ... set other fields (same as employeeToUpdate)

        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(existingEmployee));
        Mockito.when(positionRepository.findById(1L)).thenReturn(Optional.of(position));
        Mockito.when(employeeRepository.save(existingEmployee)).thenReturn(updatedEmployee);

        // **Act**
        Employee result = employeeService.updateEmployee(employeeToUpdate, 1L);

        // **Assert**
        // Se verifica que el resultado sea el mismo que el empleado actualizado
        Assertions.assertThat(result).isEqualTo(updatedEmployee);
        // Se verifica que se haya llamado al método `save` del repositorio con el empleado correcto
        Mockito.verify(employeeRepository).save(existingEmployee);
    }

    // **Test para actualizar un empleado con un ID inexistente**
    @Test(expected = EmployerNotFoundException.class)
    public void updateEmployee_whenEmployeeNotFound_thenThrowsException() {
        // **Arrange**
        Employee employeeToUpdate = new Employee();
        employeeToUpdate.setId(1L);
        // ... set other fields

        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        // **Act**
        employeeService.updateEmployee(employeeToUpdate, 1L);

        // **Assert** (verificado por la excepción esperada)
    }

    // ## Tests para `deleteEmployee`

    // **Test para eliminar un empleado exitosamente**
    @Test
    public void deleteEmployee_whenEmployeeExists_thenEmployeeIsDeleted() {
        // **Arrange**
        Employee employee = new Employee();
        employee.setId(1L);

        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        // **Act**
        boolean result = employeeService.deleteEmployee(1L);

        // **Assert**
        // Se verifica que el resultado sea `true`
        Assertions.assertThat(result).isTrue();
        // Se verifica que se haya llamado al método `delete` del repositorio con el empleado correcto
        Mockito.verify(employeeRepository).delete(employee);
    }

    // **Test para eliminar un empleado con un ID inexistente**
    @Test(expected = EmployerNotFoundException.class)
    public void deleteEmployee_whenEmployeeNotFound_thenThrowsException() {
        // **Arrange**
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        // **Act**
        employeeService.deleteEmployee(1L);

        // **Assert** (verificado por la excepción esperada)
    }

    // ## Tests para `getEmployeeId`

    // **Test para obtener un empleado por su ID exitosamente**
    @Test
    public void getEmployeeId_whenEmployeeExists_thenEmployeeIsReturned() {
        // **Arrange**
        Employee employee = new Employee();
        employee.setId(1L);

        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        // **Act**
        Optional<Employee> result = employeeService.getEmployeeId(1L);

        // **Assert**
        // Se verifica que el resultado no esté vacío
        Assertions.assertThat(result).isNotEmpty();
        // Se verifica que el contenido del resultado sea el mismo que el empleado buscado
        Assertions.assertThat(result.get()).isEqualTo(employee);
    }

    // **Test para obtener un empleado con un ID inexistente**
    @Test(expected = EmployerNotFoundException.class)
    public void getEmployeeId_whenEmployeeNotFound_thenThrowsException() {
        // **Arrange**
        Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        // **Act**
        employeeService.getEmployeeId(1L);

        // **Assert** (verificado por la excepción esperada)
    }

    // ## Tests para `getAllEmployees`

    // **Test para obtener todos los empleados exitosamente**
    @Test
    public void getAllEmployees_whenEmployeesExist_thenEmployeesAreReturned() {
        // **Arrange**
        List<Employee> employees = Arrays.asList(new Employee(), new Employee());

        Mockito.when(employeeRepository.findAll()).thenReturn(employees);

        // **Act**
        Optional<List<Employee>> result = employeeService.getAllEmployees();

        // **Assert**
        // Se verifica que el resultado no esté vacío
        Assertions.assertThat(result).isNotEmpty();
        // Se verifica que el contenido del resultado sea la lista de empleados
        Assertions.assertThat(result.get()).isEqualTo(employees);
    }

    // **Test para obtener todos los empleados cuando no hay ninguno**
    @Test
    public void getAllEmployees_whenNoEmployeesExist_thenEmptyListIsReturned() {
        // **Arrange**
        Mockito.when(employeeRepository.findAll()).thenReturn(Collections.emptyList());

        // **Act**
        Optional<List<Employee>> result = employeeService.getAllEmployees();

        // **Assert**
        // Se verifica que el resultado esté vacío
        Assertions.assertThat(result).isEmpty();
    }
}