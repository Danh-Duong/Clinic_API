package com.example.Clinic_API.controller;

import com.example.Clinic_API.enums.ResponseCode;
import com.example.Clinic_API.payload.CommentRequest;
import com.example.Clinic_API.payload.StringResponse;
import com.example.Clinic_API.service.CommentService;
import jdk.jfr.Frequency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

//    @Autowired
//    CommentService commentService;
//
//    // lấy comment của 1 bác sĩ
//    @GetMapping("/doctor/{doctorId}")
//    public ResponseEntity<?> getCommentDoctor(@PathVariable Long doctorId, @RequestParam(required = false, defaultValue = "10") Integer limit){
//        return ResponseEntity.ok(commentService.getCommentDoctor(limit,doctorId));
//    }
//
//    // tạo bình luận cho bác sĩ
//    // không có ảnh
//    @PostMapping("/doctor/{doctorId}/create")
//    public ResponseEntity<?> createCommentDoctor(@PathVariable Long doctorId, @RequestBody CommentRequest commentRequest){
//        commentService.createDoctorComment(doctorId,commentRequest);
//        StringResponse response=new StringResponse();
//        response.setMessage("Create comment Success");
//        response.setResponseStatus(ResponseCode.SUCCESS.name());
//        response.setResponseCode(ResponseCode.SUCCESS.getCode());
//        return ResponseEntity.ok(response);
//    }
//
//    // chỉnh sửa bình luận của bác sĩ
//    @PutMapping("/doctor/{commentId}/update")
//    public ResponseEntity<?> updateCommentDoctor(@PathVariable Long commentId, @RequestBody CommentRequest commentRequest){
//        commentService.updateDoctorComment(commentId,commentRequest);
//        StringResponse response=new StringResponse();
//        response.setMessage("update comment Success");
//        response.setResponseStatus(ResponseCode.SUCCESS.name());
//        response.setResponseCode(ResponseCode.SUCCESS.getCode());
//        return ResponseEntity.ok(response);
//    }
//
//    // xóa comment của bác sĩ
//    @DeleteMapping("doctor/{commentId}/delete")
//    public ResponseEntity<?> deleteCommemtDoctor(@PathVariable Long commentId){
//        commentService.deleteDoctorComment(commentId);
//        StringResponse response=new StringResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.name(),"Delete comment doctor success");
//        return ResponseEntity.ok(response);
//    }
//
//    // lấy danh sách bình luận bài post của người dùng
//    @GetMapping("/post/{postId}")
//    public ResponseEntity<?> getPostComment(@PathVariable Long postId, @RequestParam(required = false, defaultValue = "10") Integer limit){
//        return ResponseEntity.ok(commentService.getCommentPost(postId,limit));
//    }
//
//    // tạo bình luận
//    @PostMapping(value = "/post/{postId}/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<?> createPostComment(@PathVariable Long postId, @ModelAttribute CommentRequest commentRequest){
//        commentService.createPostComment(postId,commentRequest.getImage(),commentRequest.getContent());
//        StringResponse response=new StringResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.name(),"Create comment post success");
//        return ResponseEntity.ok(response);
//    }
//
//    @PutMapping(value = "/post/{commentId}/update", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<?> updatePostComment(@PathVariable Long commentId, @ModelAttribute CommentRequest commentRequest){
//        commentService.updatePostComment(commentId,commentRequest.getImage(), commentRequest.getContent());
//        StringResponse response=new StringResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.name(), "Update comment post Success");
//        return ResponseEntity.ok(response);
//    }
//
//    @DeleteMapping("/post/{commentId}/delete")
//    public ResponseEntity<?> deletePostComment(@PathVariable Long commentId){
//        commentService.deletePostComment(commentId);
//        StringResponse response=new StringResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.name(), "Delete comment post Success");
//        return ResponseEntity.ok(response);
//    }
}
