package com.plum.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsuranceEntity implements Serializable {

    @JsonProperty("type")
    private String insuranceType;

    @JsonProperty("category")
    private String category;

    @JsonProperty("policy_cover")
    private Integer policyCover;

    @JsonProperty("policy_period")
    private Integer policyPeriod;
}
