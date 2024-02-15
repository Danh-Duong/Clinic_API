package com.example.Clinic_API.repository;

import com.example.Clinic_API.entities.AttachmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentTypeRepository extends JpaRepository<AttachmentType, Long> {
    AttachmentType findByCode(String code);
}
