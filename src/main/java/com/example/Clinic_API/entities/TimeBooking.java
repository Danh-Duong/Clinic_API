package com.example.Clinic_API.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class TimeBooking extends BaseEntity{
    // mặc định sẽ là cả tháng trừ đi chủ nhật và thứ bảy
    // 1 số múi giờ có thể tự tạo
    private Date fromDate;
    private Date toDate;
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}
