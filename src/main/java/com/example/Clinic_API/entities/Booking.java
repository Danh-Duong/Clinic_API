package com.example.Clinic_API.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name = "Booking")
public class Booking extends BaseEntity{
    private String namePatient;
    private String gender;
    private String phone;
    private String address;
    // triệu chứng/ mô tả
    private String pathology;

    // Email nhận thông báo đặt lịch
    private String email;

    // ngày thực hiện đặt lịch
    private Date dateBooking;
    private String status;

    // người thực hiện đặt lịch
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    public User user;

    // đặt lịch bác sĩ nào
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @JsonIgnore
    public User doctor;

    // thời gian khám bệnh
    @OneToOne
    @JoinColumn(name = "appointment_id")
    @JsonIgnore
    private Appointment appointment;
}
