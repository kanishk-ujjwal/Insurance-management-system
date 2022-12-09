package com.plum.enums;


import com.fasterxml.jackson.annotation.JsonValue;

public enum InsuranceTypeEnum {
    ORGANISATION("Orgasniation"),
    EMPLOYEE("Employee"),
    FAMILY("Family"),
    PERSONAL("Personal");

    @JsonValue
    private final String insuranceType;

    InsuranceTypeEnum(String insuranceType) {
        this.insuranceType = insuranceType;
    }

    public String getInsuranceType() {
        return this.insuranceType;
    }

}
