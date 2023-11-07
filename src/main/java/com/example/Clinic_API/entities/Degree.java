package com.example.Clinic_API.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class Degree extends BaseEntity{
    private String name;
    private String code;
    private Boolean isActive;
    private Date dateExpired;

    @ManyToMany(mappedBy = "degrees")
    private List<User> users;
}
