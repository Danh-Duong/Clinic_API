package com.example.Clinic_API.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class Clinic extends BaseEntity{

    private String vietName;
    private String engName;

    private String code;

    private String address;

    private String phone;

    private String email;

    private String urlInfo;

    private String urlAvatar;

    // phát sinh thêm 1 trường để lấy dữ liệu tìm kiếm - lọc bệnh viện
//    @Column(name = "district_id")
//    private int districtId;

    @ManyToOne
    @JoinColumn(name = "district_id", insertable = false, updatable = false)
    @JsonIgnore
    private District district;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "clinic_faculty", joinColumns = @JoinColumn(name = "clinic_id"),
    inverseJoinColumns = @JoinColumn(name = "faculty_id"))
    private List<Faculty> faculties;

    // bệnh nhân và bác sĩ
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "clinic_user", joinColumns = @JoinColumn(name = "clinic_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;


    @Column(name = "clinicType_id")
    private Long clinicTypeId;

    @ManyToOne
    @JoinColumn(name = "clinicType_id", insertable = false, updatable = false)
    @JsonIgnore
    private ClinicType clinicType;

//    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL)
//    private List<Booking> bookings;

    @ManyToOne
    @JoinColumn(name = "user_create_id")
    @JsonIgnore
    private User userCreate;
}
