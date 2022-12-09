package com.plum.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum GenderEnum {
    Male("Male"),
    Female("Female"),
    Other("Other");

    @JsonValue
    private final String name;
    private static final Map<String, GenderEnum> lookUpMap = new HashMap<>();

    static {
        for (GenderEnum gender : GenderEnum.values()) {
            lookUpMap.put(gender.getName(), gender);
        }
    }
    public String getName() {
        return this.name;
    }
    public static GenderEnum getGenderByName(String name) {
        if (Objects.isNull(name)) {
            return null;
        }
        return lookUpMap.get(name);
    }
}
