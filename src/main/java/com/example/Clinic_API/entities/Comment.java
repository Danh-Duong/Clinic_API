package com.example.Clinic_API.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Comment extends BaseEntity{
    private String content;

    // người comment
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    // comment bác sĩ nào
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @JsonIgnore
    private User doctor;

    // comment bài post nào
    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;

    // bình luận phòng khám nào
    @ManyToOne
    @JoinColumn(name = "clinic_id")
    @JsonIgnore
    private Clinic clinic;

    // chứa hình ảnh
    @OneToOne(mappedBy = "comment")
    private Attachment attachment;
}
