package com.reliaquest.api.service;

import com.reliaquest.api.model.Employee;
import com.reliaquest.api.model.EmployeeInput;
import java.util.List;

public interface EmployeeUtilsService {

    List<Employee> fetchAllEmployees();

    Employee fetchEmployeeDetailsById(String employeeId);

    Employee addEmployeeDetails(EmployeeInput employeeInput);

    Boolean deleteEmployeeByName(String employeeName);
}
