package com.reliaquest.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    private UUID id;

    @JsonProperty(value = "employee_name")
    private String name;

    @JsonProperty(value = "employee_salary")
    private Integer salary;

    @JsonProperty(value = "employee_age")
    private Integer age;

    @JsonProperty(value = "employee_title")
    private String title;

    @JsonProperty(value = "employee_email")
    private String email;
}
