package com.nn.castor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nn.castor.domain.Employee;
import com.nn.castor.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
class EmployeeControllerTests {

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    void testGetAllEmployees() throws Exception {
        Employee employee = new Employee();
        List<Employee> employees = List.of(employee);

        given(employeeService.getAllEmployees()).willReturn(Optional.of(employees));

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    void testGetEmployeeById() throws Exception {
        Employee employee = new Employee();
        employee.setId(1L);
        given(employeeService.getEmployeeId(anyLong())).willReturn(Optional.of(employee));

        mockMvc.perform(get("/api/employees/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void testCreateEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setId(1L);
        String employeeJson = "{\"id\":1,\"name\":\"John\"}";
        MockMultipartFile photoFile = new MockMultipartFile("photo", "photo.jpg", "image/jpeg", "some-image".getBytes());
        MockMultipartFile employeePart = new MockMultipartFile("employee", "", "application/json", employeeJson.getBytes());

        given(objectMapper.readValue(employeeJson, Employee.class)).willReturn(employee);
        given(employeeService.saveNewEmployee(any(Employee.class), any(MultipartFile.class))).willReturn(employee);

        mockMvc.perform(multipart("/api/employees")
                        .file(photoFile)
                        .file(employeePart)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void testUpdateEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setId(1L);
        given(employeeService.updateEmployee(any(Employee.class), anyLong())).willReturn(employee);

        mockMvc.perform(put("/api/employees/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"John\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void testDeleteEmployee() throws Exception {
        given(employeeService.deleteEmployee(anyLong())).willReturn(true);

        mockMvc.perform(delete("/api/employees/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteEmployeeNotFound() throws Exception {
        given(employeeService.deleteEmployee(anyLong())).willReturn(false);

        mockMvc.perform(delete("/api/employees/{id}", 1L))
                .andExpect(status().isNotFound());
    }

}
