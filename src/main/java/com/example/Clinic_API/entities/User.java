package com.example.Clinic_API.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class User extends BaseEntity{
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
    private int badPoint;
    private String avatarUrl;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    // 1 bệnh viện có nhiều bác sĩ cũng như bệnh nhân
    @ManyToMany(mappedBy = "users")
    private List<Clinic> clinics;

    // người đánh giá
    @OneToMany(mappedBy = "user")
    private List<Rate> rateUsers;

    @ManyToMany
    @JoinTable(name = "user_degree", joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "degree_id"))
    private List<Degree> degrees;

    // người comment
    @OneToMany(mappedBy = "user")
    private List<Comment> commentUsers;

    // 1 bác sĩ có thể tạo nhiều lịch đặt
    @OneToMany(mappedBy = "user")
    private List<TimeBooking> timeBookings;

    // 1 người có thế đặt nhiều lịch
    @OneToMany(mappedBy = "doctor")
    private List<Booking> bookingDcotors;

    @OneToOne(mappedBy = "user")
    private EmailToken emailToken;

    @OneToMany(mappedBy = "user")
    private List<Status> statuses;

}
