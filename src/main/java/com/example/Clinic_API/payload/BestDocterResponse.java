package com.example.Clinic_API.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BestDocterResponse extends BaseResponse{
    private String name;
    private String avatarUrl;
    private double numbRate;
    private Long numbBooking;
}
