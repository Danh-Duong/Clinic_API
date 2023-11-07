package com.example.Clinic_API.repository;

import com.example.Clinic_API.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // chú ý: có thể sử dụng fielkd của @Column
    List<Comment> findByDoctorId(Long doctorId, Pageable pageable);

    List<Comment> findByPostId(Long postId, Pageable pageable);

    Comment findByPostIdAndUserId(Long postId, Long userId);


}
