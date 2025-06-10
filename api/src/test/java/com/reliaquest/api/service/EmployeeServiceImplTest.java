package com.reliaquest.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reliaquest.api.model.Employee;
import com.reliaquest.api.service.impl.EmployeeServiceImpl;
import com.reliaquest.api.utils.MockDataGenerator;
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
import org.springframework.util.CollectionUtils;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmployeeServiceImplTest {

    @InjectMocks
    EmployeeServiceImpl employeeServiceImpl;

    @MockBean
    EmployeeUtilsService employeeUtilsService;

    MockDataGenerator mockDataGenerator;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockDataGenerator = new MockDataGenerator();
    }

    @Test
    @DisplayName("Get All Employees Test")
    void testGetAll() {
        // Case : When no employee data exists
        when(employeeUtilsService.fetchAllEmployees()).thenReturn(Collections.emptyList());
        assertEquals(employeeServiceImpl.getAll(), Collections.emptyList());

        // Case : When employee data exists
        when(employeeUtilsService.fetchAllEmployees()).thenReturn(mockDataGenerator.getEmployeeMockDataList());
        List<Employee> employeeList = employeeServiceImpl.getAll();

        assertNotNull(employeeList);
        assertEquals(mockDataGenerator.getEmployeeMockDataList().size(), employeeList.size());
    }

    @Test
    @DisplayName("Employee Search by Name test")
    void testSearchEmployeesByName() {
        // Case : When employee data doesn't exists
        when(employeeUtilsService.fetchAllEmployees()).thenReturn(Collections.emptyList());
        assertEquals(employeeServiceImpl.searchEmployeesByName("Peter"), Collections.emptyList());

        // Case : When employee data exists and searchString doesn't matches with any employees name
        when(employeeUtilsService.fetchAllEmployees()).thenReturn(mockDataGenerator.getEmployeeMockDataList());
        List<Employee> unmatchedEmployees = employeeServiceImpl.searchEmployeesByName("Peter");

        assertNotNull(unmatchedEmployees);
        assertTrue(CollectionUtils.isEmpty(unmatchedEmployees));

        // Case : When employee data exists and searchString matches with some employees name
        List<Employee> nameMatchingEmployees = employeeServiceImpl.searchEmployeesByName("Malcolm");

        assertNotNull(nameMatchingEmployees);
        assertFalse(CollectionUtils.isEmpty(nameMatchingEmployees));
        assertEquals(3, nameMatchingEmployees.size());
        assertTrue(nameMatchingEmployees.get(0).getName().contains("Malcolm"));
    }

    @Test
    @DisplayName("Test to Get Employee Details By Identifier")
    void testFindEmployeeById() {
        // Case : When employee doesn't exists with respective id
        when(employeeUtilsService.fetchEmployeeDetailsById(ArgumentMatchers.anyString()))
                .thenReturn(null);
        assertNull(employeeServiceImpl.findEmployeeById("abc-45657-8989"));

        // Case : When employee exists with respective id
        when(employeeUtilsService.fetchEmployeeDetailsById(ArgumentMatchers.anyString()))
                .thenReturn(mockDataGenerator.getEmployee());
        Employee employeeData = employeeServiceImpl.findEmployeeById("abc-45657-8989");

        assertNotNull(employeeData);
        assertEquals(mockDataGenerator.getEmployee().getId(), employeeData.getId());
        assertEquals(mockDataGenerator.getEmployee().getName(), employeeData.getName());
    }

    @Test
    @DisplayName("Test to Get Highest Salary among all employees")
    void testGetHighestSalaryFromAllEmployees() {
        // Case : When employee data doesn't exists
        when(employeeUtilsService.fetchAllEmployees()).thenReturn(Collections.emptyList());
        assertEquals(employeeServiceImpl.getHighestSalaryFromAllEmployees(), 0);

        // Case : When employee data exists and highest salary is returned
        when(employeeUtilsService.fetchAllEmployees()).thenReturn(mockDataGenerator.getEmployeeMockDataList());
        Integer salary = employeeServiceImpl.getHighestSalaryFromAllEmployees();

        assertEquals(Integer.valueOf("472183"), salary);
    }

    @Test
    @DisplayName("Test to  Get Top Ten Earning Employees Names")
    void testGetNamesOfTopNEarningEmployee() {
        // Case : When employee data doesn't exists
        when(employeeUtilsService.fetchAllEmployees()).thenReturn(Collections.emptyList());
        assertEquals(employeeServiceImpl.getNamesOfTopNEarningEmployee(), Collections.emptyList());

        // Case : When employee data exists and names are returned. Total Employees are less than 10
        when(employeeUtilsService.fetchAllEmployees())
                .thenReturn(mockDataGenerator.getEmployeeMockDataList().stream()
                        .limit(8)
                        .collect(Collectors.toList()));
        List<String> employeeNames = employeeServiceImpl.getNamesOfTopNEarningEmployee();

        assertNotNull(employeeNames);
        assertFalse(CollectionUtils.isEmpty(employeeNames));
        assertEquals(8, employeeNames.size());

        // Case : When employee data exists and names are returned. Total Employees are greater than 10
        when(employeeUtilsService.fetchAllEmployees()).thenReturn(mockDataGenerator.getEmployeeMockDataList());
        employeeNames = employeeServiceImpl.getNamesOfTopNEarningEmployee();

        assertNotNull(employeeNames);
        assertFalse(CollectionUtils.isEmpty(employeeNames));
        assertEquals(10, employeeNames.size());
        assertEquals("Karoline Shields MD", employeeNames.get(0));
    }

    @Test
    @DisplayName("Employee Addition test")
    void testAddEmploye() {
        // Case : When employee addition fails
        when(employeeUtilsService.addEmployeeDetails(ArgumentMatchers.any())).thenReturn(null);
        assertNull(employeeServiceImpl.addEmployee(mockDataGenerator.getEmployeeInput()));

        // Case : When employee is added successfully
        when(employeeUtilsService.addEmployeeDetails(ArgumentMatchers.any()))
                .thenReturn(mockDataGenerator.getEmployee());
        Employee employeeData = employeeServiceImpl.addEmployee(mockDataGenerator.getEmployeeInput());

        assertNotNull(employeeData);
        assertEquals(mockDataGenerator.getEmployee().getId(), employeeData.getId());
        assertEquals(mockDataGenerator.getEmployee().getName(), employeeData.getName());
    }

    @Test
    @DisplayName("Test to Delete Employee Details By Identifier")
    void testDeleteEmployeeById() {
        // Case : When employee deletion fails
        when(employeeUtilsService.fetchEmployeeDetailsById(ArgumentMatchers.any()))
                .thenReturn(null);
        when(employeeUtilsService.deleteEmployeeByName(ArgumentMatchers.any())).thenReturn(false);
        assertEquals(employeeServiceImpl.deleteEmployeeById("abc-45657-8989"), "");

        // Case : When employee is deleted successfully
        when(employeeUtilsService.fetchEmployeeDetailsById(ArgumentMatchers.any()))
                .thenReturn(mockDataGenerator.getEmployee());
        when(employeeUtilsService.deleteEmployeeByName(ArgumentMatchers.any())).thenReturn(true);
        String employeeName = employeeServiceImpl.deleteEmployeeById("abc-45657-8989");

        assertEquals(mockDataGenerator.getEmployee().getName(), employeeName);
    }
}
