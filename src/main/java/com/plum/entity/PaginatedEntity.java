package com.plum.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PaginatedEntity implements Serializable {

    @JsonProperty("org_id")
    private Integer orgId;

    @JsonProperty("employee_count")
    private Integer employeeCount;

    @JsonProperty("employee_details")
    private List<EmployeeEntity> employeeDetails;
}
