package com.example.Clinic_API.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class ClinicType extends BaseEntity{
    private String name;
    private String code;
    private Boolean isActive;

    @OneToMany(mappedBy = "clinicType")
    private List<Clinic> clinics;
}
