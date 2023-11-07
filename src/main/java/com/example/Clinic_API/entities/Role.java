package com.example.Clinic_API.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Role extends BaseEntity{
    private String name;
    private String code;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

}
