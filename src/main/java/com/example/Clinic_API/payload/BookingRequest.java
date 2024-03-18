package com.example.Clinic_API.payload;

import com.example.Clinic_API.entities.Appointment;
import com.example.Clinic_API.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {
    private String namePatient;
    private String gender;
    private String phone;
    private String address;
    // triệu chứng/ mô tả
    private String pathology;

    // Email nhận thông báo đặt lịch
    @NotNull
    private String email;

    // đặt lịch bác sĩ nào
    @NotNull
    public Long doctorId;

    // thời gian khám bệnh
    private Long appointmentId;
}
