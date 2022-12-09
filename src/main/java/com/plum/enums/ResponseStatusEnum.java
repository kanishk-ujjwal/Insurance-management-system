package com.plum.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ResponseStatusEnum {
    SUCCESS("success"),
    FAILURE("failure"),
    UNPROCESSED("unprocessed");

    @JsonValue
    private final String status;

    ResponseStatusEnum(String status) {
        this.status = status;
    }
}
