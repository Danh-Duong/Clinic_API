package com.example.Clinic_API.repository;

import com.example.Clinic_API.entities.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    public Faculty findByCode(String code);

    public Faculty findByName(String name);
}
