package com.example.Clinic_API.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatusNumResponse extends BaseResponse{
    private Long numStatus;

    public StatusNumResponse(String responseCode, String responseStatus, Long numStatus) {
        super(responseCode, responseStatus);
        this.numStatus = numStatus;
    }
}
