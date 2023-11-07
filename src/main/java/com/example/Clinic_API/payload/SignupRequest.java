package com.example.Clinic_API.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String password;

    private String birthDate;
    private String gender;
    private String phone;

    @Email(message = "Email Format is incorrect")
    private String email;

    // đường dẫn hình ảnh đại diện của người dùng
    private String imgaeUrl;

    private String roleName;
}
