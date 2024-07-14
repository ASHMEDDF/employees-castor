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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class EmployeeService {


    private EmployeeRepository employeeRepository;
    private PositionRepository positionRepository;

    private static final String IMAGE_DIR = "src/main/resources/images";

    public Employee saveNewEmployee(Employee employeeToCreate, MultipartFile photoFile) throws IOException {

        Position position = positionRepository.findById(employeeToCreate.getPosition().getId())
                .orElseThrow(() -> new PositionNotFoundException(employeeToCreate.getPosition().getId()));
        Employee byNationalId = employeeRepository.findByIdentification(employeeToCreate.getIdentification());

        if (byNationalId != null) {
            throw new EmployeeAlreadyExistsException(employeeToCreate.getIdentification());
        }

        String photoPath = saveImage(photoFile);

        Employee newEmployee = new Employee();
        newEmployee.setIdentification(employeeToCreate.getIdentification());
        newEmployee.setName(employeeToCreate.getName());
        newEmployee.setPhotoPath(photoPath);
        newEmployee.setDateEntry(employeeToCreate.getDateEntry());
        newEmployee.setPosition(position);

        return employeeRepository.save(newEmployee);
    }

    private String saveImage(MultipartFile photoFile) throws IOException {
        String fileName = UUID.randomUUID() + "_" + photoFile.getOriginalFilename();
        Path imagePath = Paths.get(IMAGE_DIR, fileName);
        Files.createDirectories(imagePath.getParent());
        Files.copy(photoFile.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
        return imagePath.toString();
    }

    public Employee updateEmployee (@NonNull Employee employeeToUpdate, Long idEmployee) {

        Employee employee = employeeRepository.findById(idEmployee)
                .orElseThrow(() -> new EmployerNotFoundException(idEmployee));

        employee.setIdentification(employeeToUpdate.getIdentification());
        employee.setName(employeeToUpdate.getName());
        employee.setPhotoPath(employeeToUpdate.getPhotoPath());
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
