package com.example.Clinic_API.repository;

import com.example.Clinic_API.entities.EmailToken;
import com.example.Clinic_API.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailTokenRepository extends JpaRepository<EmailToken, Long> {
    public EmailToken findByToken(String token);
//
    public EmailToken findByUser(User user);
}
