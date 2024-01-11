package com.example.Clinic_API.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProvinceResponse {
    private String name;
    private int code;
    private String division_type;
    private String codename;
    public int phone_code;
    private List<DistrictResponse> districts;
}
