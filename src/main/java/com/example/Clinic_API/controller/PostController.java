package com.example.Clinic_API.controller;

import com.example.Clinic_API.enums.ResponseCode;
import com.example.Clinic_API.payload.StringResponse;
import com.example.Clinic_API.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/post")
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("/getPosts")
    public ResponseEntity<?> getPosts(@RequestParam Long userId, @RequestParam(required = false) Integer limit){
        return ResponseEntity.ok(postService.getPosts(userId,limit));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestParam(required = false) String title, @RequestParam String content, @RequestParam Long postTypeId, @RequestParam(required = false) MultipartFile[] files){
        postService.createPost(title,content,postTypeId,files);
        StringResponse response=new StringResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.name(), "Create blog Sucess");
        return ResponseEntity.ok(response);
    }
    //
    @PutMapping("update/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long postId,@RequestParam(required = false) String title, @RequestParam(required = false) String content, @RequestParam(required = false) Long postTypeId, @RequestParam(required = false) MultipartFile[] files){
        postService.updatePost(postId,title,content, postTypeId,files);
        StringResponse response=new StringResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.name(), "Update post success");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
        StringResponse response=new StringResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.name(), "Delete post success");
        return ResponseEntity.ok(response);
    }

    // bày tỏ cảm xúc về bài viết
    // bao gồm cả thêm và cập nhập cảm xúc
    @PostMapping("/status/{postId}")
    public ResponseEntity<?> updateStatus(@PathVariable Long postId,@RequestParam String status){
        postService.updateStatus(postId,status);
        StringResponse response=new StringResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.name(), "Update status success");
        return ResponseEntity.ok(response);
    }
}
