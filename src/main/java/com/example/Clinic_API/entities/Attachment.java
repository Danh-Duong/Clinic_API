package com.example.Clinic_API.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class Attachment extends BaseEntity{
    private String url;
    private boolean isActive=true;

    // 1 bài post sẽ có nhiều attachment
    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "clinic_id")
    @JsonIgnore
    private Clinic clinic;

    @ManyToOne
    @JoinColumn(name = "attachmentType_id")
    @JsonIgnore
    private AttachmentType attachmentType;

    public Attachment(String url, Post post, User user, Clinic clinic, AttachmentType attachmentType) {
        this.url = url;
        this.post = post;
        this.user = user;
        this.clinic = clinic;
        this.attachmentType = attachmentType;
    }
}
