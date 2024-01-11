package com.example.Clinic_API.payload;

import com.example.Clinic_API.entities.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClinicResponse {
    private String vietName;
    private String engName;
    private String code;
    private String address;
    private String phone;
    private String email;
    private String avatarUrl;
    private List<FacultyResponse> facultyResponses;
}
