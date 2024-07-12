package com.nn.castor.service;

import com.nn.castor.domain.Employee;
import com.nn.castor.domain.Position;
import com.nn.castor.exception.EmployeeAlreadyExistsException;
import com.nn.castor.exception.EmployerNotFoundException;
import com.nn.castor.exception.PositionNotFoundException;
import com.nn.castor.repository.EmployeeRepository;
import com.nn.castor.repository.PositionRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private PositionRepository positionRepository;

    public Employee saveNewEmployee (@NonNull Employee employeeToCreate) {

        Position position = positionRepository.findById(employeeToCreate.getPosition().getId())
                .orElseThrow(PositionNotFoundException::new);
        Employee byNationalId = employeeRepository.findByNationalId(employeeToCreate.getNationalId());

        if (byNationalId != null) {
            throw new EmployeeAlreadyExistsException(employeeToCreate.getNationalId());
        }

        Employee newEmployee = new Employee();
        newEmployee.setNationalId(employeeToCreate.getNationalId());
        newEmployee.setName(employeeToCreate.getName());
        newEmployee.setPhoto(employeeToCreate.getPhoto());
        newEmployee.setDateEntry(employeeToCreate.getDateEntry());
        newEmployee.setPosition(position);

        return employeeRepository.save(newEmployee);
    }

    public Employee updateEmployee (@NonNull Employee employeeToUpdate, Long idEmployee) {

        Employee employee = employeeRepository.findById(idEmployee)
                .orElseThrow(() -> new EmployerNotFoundException(idEmployee));

        employee.setNationalId(employeeToUpdate.getNationalId());
        employee.setName(employeeToUpdate.getName());
        employee.setPhoto(employeeToUpdate.getPhoto());
        employee.setDateEntry(employeeToUpdate.getDateEntry());
        employee.setPosition(employeeToUpdate.getPosition());

        return employeeRepository.save(employee);
    }

    public Boolean deleteEmployee (@NonNull  Long idEmployee) {

        Optional<Employee> employee = employeeRepository.findById(idEmployee);

        if (employee.isPresent()){
            employeeRepository.delete(employee.get());
            return true;
        }
        throw new EmployerNotFoundException(idEmployee);
    }

    public Optional<Employee> getEmployeeId (@NonNull  Long idEmployee) {

        Optional<Employee> employee = employeeRepository.findById(idEmployee);

        if (employee.isPresent()){
            return employee;
        }
        throw new EmployerNotFoundException(idEmployee);
    }

    public Optional<List<Employee>> getAllEmployees () {
        return Optional.of(employeeRepository.findAll());
    }
}
