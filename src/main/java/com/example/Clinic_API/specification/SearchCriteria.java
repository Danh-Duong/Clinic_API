package com.example.Clinic_API.specification;

import com.example.Clinic_API.enums.OperationEnum;
import lombok.Data;

import java.util.List;

@Data
public class SearchCriteria {
    private String key;
    private OperationEnum operation;
    private String value;

    private List<String> values;

    public SearchCriteria(String key, OperationEnum operation, String value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    public SearchCriteria(String key, OperationEnum operation, List<String> values) {
        this.key = key;
        this.operation = operation;
        this.values = values;
    }
}
