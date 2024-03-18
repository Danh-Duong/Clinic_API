package com.example.Clinic_API.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public enum ResponseCode {

    SUCCESS("01"),
    ERROR("02"),
    DUPLICATE_VALUE("03"),
    MISSING_VALUE("04"),
    NOT_FOUND("05"),

    RESPONSING("06");

    private String code;

}
