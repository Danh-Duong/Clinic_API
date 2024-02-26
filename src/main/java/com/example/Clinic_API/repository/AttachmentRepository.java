package com.example.Clinic_API.repository;

import com.example.Clinic_API.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    @Transactional
    public void deleteAttachmentByPost(Post post);

}
