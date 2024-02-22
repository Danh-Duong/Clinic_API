package com.example.Clinic_API.repository;

import com.example.Clinic_API.entities.Post;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByUserId(Long userId);


    // lấy những bài post cá nhân
    @Query(nativeQuery = true, value = "Select * from post " +
            "where post.user_id = :userId and post.clinic_id is NULL")
    List<Post> findByUserPost(Long userId, Pageable pg);
    List<Post> findByClinicId(Long clnicId,Pageable pg);
}
