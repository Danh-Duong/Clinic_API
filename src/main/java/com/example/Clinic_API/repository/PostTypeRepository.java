package com.example.Clinic_API.repository;

import com.example.Clinic_API.entities.PostType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostTypeRepository extends JpaRepository<PostType,Long> {
}
