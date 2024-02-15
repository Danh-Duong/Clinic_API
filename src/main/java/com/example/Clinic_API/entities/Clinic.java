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

    // ở đây có thể là link facebook, hay link gg
    private String urlInfo;

    @ManyToOne
    @JoinColumn(name = "district_id", insertable = false, updatable = false)
    @JsonIgnore
    private District district;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "clinic_faculty", joinColumns = @JoinColumn(name = "clinic_id"),
            inverseJoinColumns = @JoinColumn(name = "faculty_id"))
    private List<Faculty> faculties;

    // chứa thông tin bác sĩ và bệnh nhân
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "clinic_user", joinColumns = @JoinColumn(name = "clinic_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    // người tạo ra clinic
    @OneToOne
    @JoinColumn(name = "user_create_id")
    private User userCreate;

    @OneToMany(mappedBy = "clinic")
    private List<Post> posts;

    @OneToMany(mappedBy = "clinic")
    private List<Rate> rates;

    @OneToMany(mappedBy = "clinic")
    private List<Attachment> attachments;
}
