package com.example.Clinic_API.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NotNull
public class ChangePassRequest {

    @NotNull(message = "Old Password is required")
    private String oldPass;

    @Length(min = 5, max = 100, message = "This length of password is between from 5 to 100")
    @NotNull(message = "New Password is required")
    private String newPass;
}
