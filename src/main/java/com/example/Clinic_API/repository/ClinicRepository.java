package com.example.Clinic_API.repository;

import com.example.Clinic_API.entities.Clinic;
import com.example.Clinic_API.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long>, JpaSpecificationExecutor<Clinic> {
    Clinic findByAddress(String address);

    Clinic findByUserCreate(User user);

    Clinic findByVietName(String vietName);
}
