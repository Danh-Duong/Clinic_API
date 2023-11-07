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
@Table
@Entity
public class Booking extends BaseEntity{

    private String namePatient;
    private String gender;
    private String phone;
    private String address;
    private String pathology;

    // Email nhận thông báo (thành công)
    private String email;
    // ngày thực hiện đặt lịch
    private Date dateBooking;
    // người đặt lịch
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    // ở đây sẽ có 2 loại
    // - booking bác sĩ
    // - booking phòng khám (trong này có khoa cụ thể)
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @JsonIgnore
    private User doctor;

//    @ManyToOne
//    @JoinColumn(name = "clinic_id")
//    @JsonIgnore
//    private Clinic clinic;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    @JsonIgnore
    private Faculty faculty;

    // thời gian khám bệnh
    @OneToOne
    @JoinColumn(name = "timeBooking_id")
    private TimeBooking timeBooking;

}
