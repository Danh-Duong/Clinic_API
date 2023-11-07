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
    private String VietName;

    private String address;

    private String urlAvatar;
}
