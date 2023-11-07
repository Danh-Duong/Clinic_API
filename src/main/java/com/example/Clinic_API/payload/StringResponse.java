package com.example.Clinic_API.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StringResponse extends BaseResponse{
    private String message;

    public StringResponse(String responseCode, String responseStatus, String message) {
        super(responseCode, responseStatus);
        this.message = message;
    }
}
