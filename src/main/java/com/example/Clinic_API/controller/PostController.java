package com.example.Clinic_API.controller;

import com.example.Clinic_API.enums.ResponseCode;
import com.example.Clinic_API.payload.PostRequest;
import com.example.Clinic_API.payload.StringResponse;
import com.example.Clinic_API.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/post")
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("/getPosts")
    public ResponseEntity<?> getPosts(@RequestParam(required = false) Long userId,
                                      @RequestParam(required = false) Long clinicId,
                                      @RequestParam(required = false, defaultValue = "10") Integer limit,
                                      @RequestParam(required = false, defaultValue = "1") Integer page,
                                      @RequestParam(required = false) String type){
        return ResponseEntity.ok(postService.getPosts(userId,clinicId,limit, page));
    }

    @PostMapping(value = "/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createPost(@ModelAttribute PostRequest postRequest, @RequestParam String type){
        postService.createPost(postRequest, type);
        StringResponse response=new StringResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.name(), "Create blog Sucess");
        return ResponseEntity.ok(response);
    }

    @PutMapping("update/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long postId,@ModelAttribute PostRequest postRequest){
        postService.updatePost(postId,postRequest);
        StringResponse response=new StringResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.name(), "Update Post Successfully!");
        return ResponseEntity.ok(response);
    }
//
//    @DeleteMapping("/delete/{postId}")
//    public ResponseEntity<?> deletePost(@PathVariable Long postId){
//        postService.deletePost(postId);
//        StringResponse response=new StringResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.name(), "Delete post success");
//        return ResponseEntity.ok(response);
//    }
//
//    // bày tỏ cảm xúc về bài viết
//    // bao gồm cả thêm và cập nhập cảm xúc
//    @PostMapping("/status/{postId}")
//    public ResponseEntity<?> updateStatus(@PathVariable Long postId,@RequestParam String status){
//        postService.updateStatus(postId,status);
//        StringResponse response=new StringResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.name(), "Update status success");
//        return ResponseEntity.ok(response);
//    }
}
