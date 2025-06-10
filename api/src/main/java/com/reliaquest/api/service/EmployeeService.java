package com.reliaquest.api.service;

import com.reliaquest.api.model.Employee;
import com.reliaquest.api.model.EmployeeInput;
import java.util.List;

public interface EmployeeService {
    List<Employee> getAll();

    List<Employee> searchEmployeesByName(String searchString);

    Employee findEmployeeById(String id);

    Integer getHighestSalaryFromAllEmployees();

    List<String> getNamesOfTopNEarningEmployee();

    Employee addEmployee(EmployeeInput employeeInput);

    String deleteEmployeeById(String id);
}
