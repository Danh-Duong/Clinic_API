package com.example.Clinic_API.repository;

import com.example.Clinic_API.entities.Post;
import com.example.Clinic_API.entities.StatusPost;
import com.example.Clinic_API.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<StatusPost, Long> {
//    StatusPost findByUserAndPost(User user, Post post);
}
