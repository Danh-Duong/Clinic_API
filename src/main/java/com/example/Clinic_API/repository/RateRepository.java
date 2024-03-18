package com.example.Clinic_API.repository;

import com.example.Clinic_API.entities.Clinic;
import com.example.Clinic_API.entities.Rate;
import com.example.Clinic_API.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {
    public List<Rate> findByClinic(Clinic clinic);
    public List<Rate> findByDoctor(User doctor);

    public boolean existsByClinicAndUser(Clinic clinic, User user);
    public boolean existsByDoctorAndUser(User doctor, User user);
}
