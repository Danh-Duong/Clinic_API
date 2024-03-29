package com.example.Clinic_API.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Post extends BaseEntity{
    private String title;
    private String content;

    // 1 bài đăng có thể có nhiều hình ảnh
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Attachment> attachments;

    @ManyToOne
    @JoinColumn(name = "postType_id")
    @JsonIgnore
    private PostType postType;

    // người tạo bài viết
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    // phòng khám tạo bài post
    @ManyToOne
    @JoinColumn(name = "clinic_id")
    @JsonIgnore
    private Clinic clinic;

    @OneToMany(mappedBy = "post")
    List<Comment> comments;

    @OneToMany(mappedBy = "post")
    private List<StatusPost> statusPosts;
}
