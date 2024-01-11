package com.example.Clinic_API.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class User extends BaseEntity{
    // tên đăng nhập
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    // tên người dùng hiển thị
    // vd: Dương Ngọc Danh
    private String name;
    private Date birthDate;
    private String gender;
    private String phone;
    private String email;
    private int badPoint=0;
    private String avatarUrl;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    // 1 bác sĩ chỉ cho trong 1 clinic
    // 1 bệnh viện có nhiều bác sĩ và bệnh nhân
    @ManyToMany(mappedBy = "users")
    private List<Clinic> clinics;

    @OneToOne(mappedBy = "userCreate")
    private Clinic clinicCreate;

    // người đánh giá
    @OneToMany(mappedBy = "user")
    private List<Rate> rateUsers;

    // bác sĩ được đánh giá
    @OneToMany(mappedBy = "doctor")
    private List<Rate> rateDoctors;

    @ManyToMany
    @JoinTable(name = "user_degree", joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "degree_id"))
    private List<Degree> degrees;

    // người comment
    @OneToMany(mappedBy = "user")
    private List<Comment> commentUsers;

    // người comment
    @OneToMany(mappedBy = "doctor")
    private List<Comment> commentDoctors;

    // 1 bác sĩ có thể tạo nhiều lịch khám
    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;

    // 1 người dùng có thể đặt nhiều lịch
    @OneToMany(mappedBy = "user")
    private List<Booking> bookingUsers;

    // 1 bác sĩ có nhiều lịch đặt
    @OneToMany(mappedBy = "doctor")
    private List<Booking> bookingDoctors;

    @OneToOne(mappedBy = "user")
    private EmailToken emailToken;

    @OneToMany(mappedBy = "user")
    private List<StatusPost> statusPosts;

    // ở đây lưu thông tin ảnh đại diện
    // cũng như hình ảnh bằng cấp
    @OneToMany(mappedBy = "user")
    private List<Attachment> attachments;

    // một bác sĩ có thể tạo ra nhiều bảng giá
//    @OneToMany(mappedBy = "doctor")
//    private List<Price> prices;
}
