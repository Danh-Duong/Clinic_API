package com.example.Clinic_API.repository;

import com.example.Clinic_API.entities.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends JpaRepository<District, Integer> {
    public District findByName(String name);
}
