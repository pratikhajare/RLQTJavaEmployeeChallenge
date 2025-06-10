package com.reliaquest.api.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reliaquest.api.model.Employee;
import com.reliaquest.api.model.EmployeeInput;
import com.reliaquest.api.model.Response;
import com.reliaquest.api.service.EmployeeUtilsService;
import com.reliaquest.api.service.ExternalApiService;
import io.micrometer.common.util.StringUtils;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmployeeUtilsServiceImpl implements EmployeeUtilsService {

    @Autowired
    ExternalApiService externalApiService;

    @Value("${mock-employee.service.url}")
    String mockServiceUrl;

    @Override
    public List<Employee> fetchAllEmployees() {
        Response responseObj = (Response) externalApiService.buildRequestAndReturnResponse(
                mockServiceUrl, Response.class, HttpMethod.GET, null, new HttpHeaders(), new HashMap<>());
        if (responseObj != null && responseObj.data() != null) {
            log.info("EmployeeUtilsServiceImpl | fetchAllEmployees | All Employees data retrieved successfully");
            return new ObjectMapper().convertValue(responseObj.data(), new TypeReference<List<Employee>>() {});
        }
        log.info("EmployeeUtilsServiceImpl | fetchAllEmployees | Data retrieval failed");
        return Collections.emptyList();
    }

    @Override
    public Employee fetchEmployeeDetailsById(String employeeId) {
        if (StringUtils.isNotBlank(employeeId)) {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("id", employeeId);
            Response responseObj = (Response) externalApiService.buildRequestAndReturnResponse(
                    mockServiceUrl.concat("/{id}"), Response.class, HttpMethod.GET, null, new HttpHeaders(), paramMap);
            if (responseObj != null && responseObj.data() != null) {
                log.info(
                        "EmployeeUtilsServiceImpl | fetchEmployeeDetailsById | Employee Details retrieved successfully");
                return new ObjectMapper().convertValue(responseObj.data(), new TypeReference<Employee>() {});
            }
            log.info("EmployeeUtilsServiceImpl | fetchEmployeeDetailsById | Employee Details retrieval failed");
        }
        return null;
    }

    @Override
    public Employee addEmployeeDetails(EmployeeInput employeeInput) {
        if (employeeInput != null) {
            Response responseObj = (Response) externalApiService.buildRequestAndReturnResponse(
                    mockServiceUrl, Response.class, HttpMethod.POST, employeeInput, new HttpHeaders(), new HashMap<>());
            if (responseObj != null && responseObj.data() != null) {
                log.info("EmployeeUtilsServiceImpl | addEmployeeDetails | Employee Details added successfully");
                return new ObjectMapper().convertValue(responseObj.data(), new TypeReference<Employee>() {});
            }
            log.info("EmployeeUtilsServiceImpl | addEmployeeDetails | Employee Details addition failed");
        }
        return null;
    }

    @Override
    public Boolean deleteEmployeeByName(String employeeName) {
        if (StringUtils.isNotBlank(employeeName)) {
            Map<String, Object> deleteInputObj = new HashMap<>();
            deleteInputObj.put("name", employeeName);
            Response responseObj = (Response) externalApiService.buildRequestAndReturnResponse(
                    mockServiceUrl,
                    Response.class,
                    HttpMethod.DELETE,
                    deleteInputObj,
                    new HttpHeaders(),
                    new HashMap<>());
            if (responseObj != null && responseObj.data() != null) {

                log.info("EmployeeUtilsServiceImpl | deleteEmployeeByName | Employee deleted successfully");
                return new ObjectMapper().convertValue(responseObj.data(), new TypeReference<Boolean>() {});
            }
            log.info("EmployeeUtilsServiceImpl | deleteEmployeeByName | Employee deletion failed");
        }
        return false;
    }
}
