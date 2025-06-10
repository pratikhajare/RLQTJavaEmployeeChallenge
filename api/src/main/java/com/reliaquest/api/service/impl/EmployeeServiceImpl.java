package com.reliaquest.api.service.impl;

import com.reliaquest.api.model.Employee;
import com.reliaquest.api.model.EmployeeInput;
import com.reliaquest.api.service.EmployeeService;
import com.reliaquest.api.service.EmployeeUtilsService;
import io.micrometer.common.util.StringUtils;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeUtilsService employeeUtilsService;

    @Override
    public List<Employee> getAll() {
        List<Employee> employeeList = employeeUtilsService.fetchAllEmployees();
        if (!CollectionUtils.isEmpty(employeeList)) return employeeList;
        return Collections.emptyList();
    }

    @Override
    public List<Employee> searchEmployeesByName(String searchString) {
        List<Employee> employeeList = employeeUtilsService.fetchAllEmployees();
        if (!CollectionUtils.isEmpty(employeeList)) {

            // matching the searchString regex pattern with employee name
            Pattern p = Pattern.compile(searchString.toLowerCase());
            return employeeList.stream()
                    .filter(emp -> p.matcher(emp.getName().toLowerCase()).find())
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public Employee findEmployeeById(String employeeId) {
        if (StringUtils.isNotBlank(employeeId)) {
            return employeeUtilsService.fetchEmployeeDetailsById(employeeId);
        }
        return null;
    }

    @Override
    public Integer getHighestSalaryFromAllEmployees() {
        List<Employee> employeeList = employeeUtilsService.fetchAllEmployees();
        if (!CollectionUtils.isEmpty(employeeList)) {
            return employeeList.stream()
                    .map(Employee::getSalary)
                    .max(Integer::compareTo)
                    .orElse(0);
        }
        return 0;
    }

    @Override
    public List<String> getNamesOfTopNEarningEmployee() {
        List<Employee> employeeList = employeeUtilsService.fetchAllEmployees();
        if (!CollectionUtils.isEmpty(employeeList)) {
            int topEmployeesCnt = 10;

            // sorting the employee list by salary in descending order
            return employeeList.stream()
                    .sorted(Collections.reverseOrder(Comparator.comparingInt(Employee::getSalary)))
                    .limit(topEmployeesCnt)
                    .map(Employee::getName)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public Employee addEmployee(EmployeeInput employeeInput) {
        if (employeeInput != null) {
            return employeeUtilsService.addEmployeeDetails(employeeInput);
        }
        return null;
    }

    @Override
    public String deleteEmployeeById(String employeeId) {
        if (StringUtils.isNotBlank(employeeId)) {
            Employee employee = employeeUtilsService.fetchEmployeeDetailsById(employeeId);
            if (Objects.nonNull(employee)) {
                Boolean isDeleted = employeeUtilsService.deleteEmployeeByName(employee.getName());
                if (isDeleted) return employee.getName();
            }
        }
        return "";
    }
}
