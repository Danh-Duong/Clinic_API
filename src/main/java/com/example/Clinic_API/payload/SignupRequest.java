package com.example.Clinic_API.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    @NotNull(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    @Length(min = 4)
    private String password;

    private String birthDate;
    private String gender;
    private String phone;

    @Email(message = "Email Format is incorrect")
    @NotNull
    private String email;

    private String role;
}
