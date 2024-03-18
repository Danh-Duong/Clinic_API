package com.example.Clinic_API.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private String avatar;
    private String username;
    private String content;
    private Date commentAt;

    private String attachmentUrl;

    public CommentResponse(String avatar, String username, String content, Date commentAt) {
        this.avatar = avatar;
        this.username = username;
        this.content = content;
        this.commentAt = commentAt;
    }
}
