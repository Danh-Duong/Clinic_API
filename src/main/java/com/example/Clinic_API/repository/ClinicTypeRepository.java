package com.example.Clinic_API.repository;

import com.example.Clinic_API.entities.ClinicType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicTypeRepository extends JpaRepository<ClinicType, Long> {
    public ClinicType findByName(String name);
}
