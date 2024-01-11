package com.example.Clinic_API.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Price extends BaseEntity{
    // giá cả trung bình theo/ tương ứng theo khoa(dịch vụ)
    private String title;
    private Double cost;
    // dịch vụ này còn sử dụng hay không?
    private Boolean isActive;

    // bác sĩ tạo ra giá
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @JsonIgnore
    private User doctor;
}
