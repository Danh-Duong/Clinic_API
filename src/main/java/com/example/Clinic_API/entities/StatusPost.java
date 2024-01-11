package com.example.Clinic_API.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class StatusPost extends BaseEntity{
    private String status;

    // đây là người bày tỏ cảm xúc
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // bài post bày tỏ cảm xúc
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
