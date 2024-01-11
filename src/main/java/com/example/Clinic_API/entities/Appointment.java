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
@Table
public class Appointment extends BaseEntity{

    // thời gian lịch khám
    private Date fromDate;
    private Date toDate;
    //còn trống chỗ hay không
    private Boolean isVacant=true;

    // thông tin bác sĩ
    // từ thông tin của bác sĩ có thể lấy được thông tin clinic
    // vì 1 bác sĩ chỉ thuộc về 1 clinic
    // nhưng có thể có nhiều faculty
    // tại 1 phòng khám có thể khám nhiều lĩnh vực
    // lịch này là đăng ký cho tất cả các lĩnh vực (khoa)
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User doctor;

    @OneToOne(mappedBy = "appointment")
    private Booking booking;
}
