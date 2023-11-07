package com.example.Clinic_API.entities;

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
public class Faculty extends BaseEntity{
    private String name;
    private String code;
    private Boolean isActive;

    @ManyToMany(mappedBy = "faculties")
    private List<Clinic> clinics;

    @ManyToMany
    @JoinTable(name = "faculty_user", joinColumns = @JoinColumn(name = "faculty_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    // danh sách các
    @OneToMany(mappedBy = "faculty")
    private List<Booking> bookings;
}
