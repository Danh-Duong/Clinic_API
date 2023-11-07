package com.example.Clinic_API.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {

    private String name;
    private String birthDate;
    private String gender;
    private String phone;

    @Email
    private String email;

}
