package com.example.Clinic_API.repository;

import com.example.Clinic_API.entities.Post;
import com.example.Clinic_API.entities.StatusPost;
import com.example.Clinic_API.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<StatusPost, Long> {

    // kiểm tra xem người dùng đã bày tỏ cảm xúc bài post này chưa
    StatusPost findByUserAndPost(User user, Post post);

    // lấy ra số lượng người đã thả cảm xúc
    public long countByPost(Post post);

    public long countByPostAndStatus(Post post, String status);
}
