package com.reliaquest.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reliaquest.api.constants.EmployeeApiConstants;
import com.reliaquest.api.controller.impl.EmployeeController;
import com.reliaquest.api.model.Employee;
import com.reliaquest.api.service.EmployeeService;
import com.reliaquest.api.utils.MockDataGenerator;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmployeeControllerTest {

    @InjectMocks
    EmployeeController employeeController;

    @MockBean
    EmployeeService employeeService;

    private MockMvc mockMvc;

    MockDataGenerator mockDataGenerator;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeAll
    void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockDataGenerator = new MockDataGenerator();
    }

    @Test
    @DisplayName("Get All Employees Test")
    void testGetAllEmployees() throws Exception {
        // Case : When employee data doesn't exists
        when(employeeService.getAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(MockMvcRequestBuilders.get(EmployeeApiConstants.API_ENDPOINT_EMPLOYEE))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Case : When employee data exists
        when(employeeService.getAll()).thenReturn(mockDataGenerator.getEmployeeMockDataList());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(EmployeeApiConstants.API_ENDPOINT_EMPLOYEE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<Employee> employees = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<List<Employee>>() {});

        assertNotNull(employees);
        assertFalse(CollectionUtils.isEmpty(employees));
        assertEquals(mockDataGenerator.getEmployeeMockDataList().size(), employees.size());
    }

    @Test
    @DisplayName("Employee Search by Name test")
    void testGetEmployeeByNameSearch() throws Exception {
        // Case : When employee data doesn't exists or When employee data exists and searchString doesn't matches with
        // any employees name
        when(employeeService.searchEmployeesByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());
        mockMvc.perform(MockMvcRequestBuilders.get(EmployeeApiConstants.API_ENDPOINT_EMPLOYEE
                        .concat(EmployeeApiConstants.API_ENDPOINT_EMPLOYEE_SEARCH_BY_NAME)
                        .replace("{searchString}", "Malcolm")))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Case : When employee data exists and searchString matches with some employees name
        when(employeeService.searchEmployeesByName(ArgumentMatchers.anyString()))
                .thenReturn(Arrays.asList(mockDataGenerator.getEmployee()));
        MvcResult mvcResult2 = mockMvc.perform(MockMvcRequestBuilders.get(EmployeeApiConstants.API_ENDPOINT_EMPLOYEE
                        .concat(EmployeeApiConstants.API_ENDPOINT_EMPLOYEE_SEARCH_BY_NAME)
                        .replace("{searchString}", "Malcolm")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<Employee> nameMatchingEmployees = objectMapper.readValue(
                mvcResult2.getResponse().getContentAsString(), new TypeReference<List<Employee>>() {});

        assertNotNull(nameMatchingEmployees);
        assertFalse(CollectionUtils.isEmpty(nameMatchingEmployees));
        assertEquals(1, nameMatchingEmployees.size());
    }

    @Test
    @DisplayName("Test to Get Employee Details By Identifier")
    void testGetEmployeeById() throws Exception {

        // Case : When employee doesn't exists with respective id
        when(employeeService.findEmployeeById(ArgumentMatchers.anyString())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get(EmployeeApiConstants.API_ENDPOINT_EMPLOYEE
                        .concat(EmployeeApiConstants.API_ENDPOINT_EMPLOYEE_GET_BY_ID)
                        .replace("{id}", "abc-45657-8989")))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        // Case : When employee exists with respective id
        when(employeeService.findEmployeeById(ArgumentMatchers.anyString()))
                .thenReturn(mockDataGenerator.getEmployee());
        MvcResult mvcResult2 = mockMvc.perform(MockMvcRequestBuilders.get(EmployeeApiConstants.API_ENDPOINT_EMPLOYEE
                        .concat(EmployeeApiConstants.API_ENDPOINT_EMPLOYEE_GET_BY_ID)
                        .replace("{id}", "abc-45657-8989")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Employee employeeData = objectMapper.readValue(mvcResult2.getResponse().getContentAsString(), Employee.class);

        assertNotNull(employeeData);
    }

    @Test
    @DisplayName("Test to Get Highest Salary among all employees")
    void testGetHighestSalaryOfEmployees() throws Exception {

        // Case : When employee data doesn't exists
        when(employeeService.getHighestSalaryFromAllEmployees()).thenReturn(0);
        mockMvc.perform(MockMvcRequestBuilders.get(EmployeeApiConstants.API_ENDPOINT_EMPLOYEE.concat(
                        EmployeeApiConstants.API_ENDPOINT_EMPLOYEE_GET_HIGHEST_SALARY)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Case : When employee data exists and highest salary is returned
        when(employeeService.getHighestSalaryFromAllEmployees())
                .thenReturn(mockDataGenerator.getEmployee().getSalary());
        MvcResult mvcResult2 = mockMvc.perform(
                        MockMvcRequestBuilders.get(EmployeeApiConstants.API_ENDPOINT_EMPLOYEE.concat(
                                EmployeeApiConstants.API_ENDPOINT_EMPLOYEE_GET_HIGHEST_SALARY)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Integer salary = objectMapper.readValue(mvcResult2.getResponse().getContentAsString(), Integer.class);

        assertEquals(mockDataGenerator.getEmployee().getSalary(), salary);
    }

    @Test
    @DisplayName("Test to  Get Top Ten Earning Employees Names")
    void testGetTopTenHighestEarningEmployeeNames() throws Exception {

        // Case : When employee data doesn't exists
        when(employeeService.getNamesOfTopNEarningEmployee()).thenReturn(Collections.emptyList());
        mockMvc.perform(MockMvcRequestBuilders.get(EmployeeApiConstants.API_ENDPOINT_EMPLOYEE.concat(
                        EmployeeApiConstants.API_ENDPOINT_EMPLOYEE_GET_TOP_TEN_EARNING_EMPLOYEES)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Case : When employee data exists and top ten highest earning employees names are returned
        when(employeeService.getNamesOfTopNEarningEmployee())
                .thenReturn(mockDataGenerator.getEmployeeMockDataList().stream()
                        .map(Employee::getName)
                        .limit(10)
                        .collect(Collectors.toList()));
        MvcResult mvcResult2 = mockMvc.perform(
                        MockMvcRequestBuilders.get(EmployeeApiConstants.API_ENDPOINT_EMPLOYEE.concat(
                                EmployeeApiConstants.API_ENDPOINT_EMPLOYEE_GET_TOP_TEN_EARNING_EMPLOYEES)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<String> employeeNames = objectMapper.readValue(
                mvcResult2.getResponse().getContentAsString(), new TypeReference<List<String>>() {});

        assertNotNull(employeeNames);
        assertFalse(CollectionUtils.isEmpty(employeeNames));
        assertEquals(10, employeeNames.size());
    }

    @Test
    @DisplayName("Employee Creation Test")
    void testCreateEmployee() throws Exception {

        // Case : When employee addition fails
        when(employeeService.addEmployee(ArgumentMatchers.any())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.post(EmployeeApiConstants.API_ENDPOINT_EMPLOYEE)
                        .content(objectMapper.writeValueAsString(mockDataGenerator.getEmployeeInput()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());

        // Case : When employee is added successfully
        when(employeeService.addEmployee(ArgumentMatchers.any())).thenReturn(mockDataGenerator.getEmployee());
        MvcResult mvcResult2 = mockMvc.perform(MockMvcRequestBuilders.post(EmployeeApiConstants.API_ENDPOINT_EMPLOYEE)
                        .content(objectMapper.writeValueAsString(mockDataGenerator.getEmployeeInput()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Employee employeeData = objectMapper.readValue(mvcResult2.getResponse().getContentAsString(), Employee.class);

        assertNotNull(employeeData);
    }

    @Test
    @DisplayName("Test to Delete Employee Details By Identifier")
    void testDeleteEmployeeById() throws Exception {

        // Case : When employee deletion fails
        when(employeeService.deleteEmployeeById(ArgumentMatchers.anyString())).thenReturn("");
        mockMvc.perform(MockMvcRequestBuilders.delete(EmployeeApiConstants.API_ENDPOINT_EMPLOYEE
                        .concat(EmployeeApiConstants.API_ENDPOINT_EMPLOYEE_DELETE_BY_ID)
                        .replace("{id}", "abc-45657-8989")))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        // Case : When employee is deleted successfully
        when(employeeService.deleteEmployeeById(ArgumentMatchers.anyString()))
                .thenReturn(mockDataGenerator.getEmployee().getName());
        MvcResult mvcResult2 = mockMvc.perform(MockMvcRequestBuilders.delete(EmployeeApiConstants.API_ENDPOINT_EMPLOYEE
                        .concat(EmployeeApiConstants.API_ENDPOINT_EMPLOYEE_DELETE_BY_ID)
                        .replace("{id}", "abc-45657-8989")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.parseMediaType("text/plain;charset=UTF-8")))
                .andReturn();

        String employeeName = mvcResult2.getResponse().getContentAsString();

        assertEquals(mockDataGenerator.getEmployee().getName(), employeeName);
    }
}
