package com.example.Clinic_API.payload;

import com.example.Clinic_API.entities.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClinicRequest {
    @NotNull
    private String vietName;
    private String engName;
    private String code;
    private String address;
    private String phone;
    private String email;
    private String urlInfo;
    @NotNull
    private List<String> facultyNames;

    private MultipartFile file;
}
