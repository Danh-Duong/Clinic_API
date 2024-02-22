package com.example.Clinic_API.payload;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostRequest {
    private String title;
    private String content;
//    private Long postTypeId;
    private Long clinicId;
    private MultipartFile[] files;
}
