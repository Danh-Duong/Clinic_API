//package com.example.Clinic_API.entities;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.util.List;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Table
//@Entity
//public class Province {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    private String name;
//
//    private String codename;
//
//    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL)
//    private List<District> districts;
//}
