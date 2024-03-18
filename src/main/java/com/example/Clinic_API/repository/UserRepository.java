package com.example.Clinic_API.repository;

import com.example.Clinic_API.entities.Degree;
import com.example.Clinic_API.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByEmail(String email);

//    @Query(nativeQuery = true, value = "select * " +
//            "from user_role as ur " +
//            "inner join clinic_user as cu on ur.user_id=cu.user_id " +
//            "inner join booking as b on b.doctor_id = cu.user_id " +
//            "where role_id=3 and ")
//    List<User> countBookingByClinic();


}
