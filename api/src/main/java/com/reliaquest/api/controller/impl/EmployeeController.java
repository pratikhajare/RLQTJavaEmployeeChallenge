package com.reliaquest.api.controller.impl;

import com.reliaquest.api.constants.EmployeeApiConstants;
import com.reliaquest.api.controller.IEmployeeController;
import com.reliaquest.api.exceptions.BaseException;
import com.reliaquest.api.exceptions.EntityNotFoundException;
import com.reliaquest.api.model.Employee;
import com.reliaquest.api.model.EmployeeInput;
import com.reliaquest.api.service.EmployeeService;
import io.micrometer.common.util.StringUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = EmployeeApiConstants.API_ENDPOINT_EMPLOYEE)
public class EmployeeController implements IEmployeeController<Employee, EmployeeInput> {

    @Autowired
    EmployeeService employeeService;

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAll());
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
        return ResponseEntity.ok(employeeService.searchEmployeesByName(searchString));
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {
        Employee employee = employeeService.findEmployeeById(id);
        if (employee != null) return ResponseEntity.ok(employee);
        throw new EntityNotFoundException(HttpStatus.NOT_FOUND, "Record does not exists");
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        return ResponseEntity.ok(employeeService.getHighestSalaryFromAllEmployees());
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        return ResponseEntity.ok(employeeService.getNamesOfTopNEarningEmployee());
    }

    @Override
    public ResponseEntity<Employee> createEmployee(EmployeeInput employeeInput) {
        Employee employee = employeeService.addEmployee(employeeInput);
        if (employee != null) return ResponseEntity.ok(employee);
        throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, "Data addition error out");
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        String employeeName = employeeService.deleteEmployeeById(id);
        if (StringUtils.isNotBlank(employeeName)) return ResponseEntity.ok(employeeName);
        throw new EntityNotFoundException(HttpStatus.NOT_FOUND, "Record does not exists");
    }
}
