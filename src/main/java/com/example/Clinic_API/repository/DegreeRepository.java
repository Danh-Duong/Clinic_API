package com.example.Clinic_API.repository;

import com.example.Clinic_API.entities.Degree;
import com.example.Clinic_API.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DegreeRepository extends JpaRepository<Degree, Long> {
    public List<Degree> findByDoctors(List<User> doctor);
}
