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

    @Autowired
    CommentService commentService;

    @GetMapping("")
    public ResponseEntity<?> getComment(@RequestParam Long doctorId, @RequestParam Long postId ,@RequestParam(required = false, defaultValue = "10") Integer limit){
        return ResponseEntity.ok(commentService.getComment(limit,doctorId, postId));
    }

    @PostMapping(value = "create", consumes ={MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createComment(@RequestParam Long doctorId,
                                           @RequestParam Long postId,
                                           @RequestBody CommentRequest commentRequest){
        commentService.createDoctorComment(doctorId,postId,commentRequest);
        StringResponse response=new StringResponse();
        response.setMessage("Create comment Successfully");
        response.setResponseStatus(ResponseCode.SUCCESS.name());
        response.setResponseCode(ResponseCode.SUCCESS.getCode());
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "update/{commentId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateComment(@PathVariable Long commentId, @RequestBody CommentRequest commentRequest){
        commentService.updateComment(commentId,commentRequest);
        StringResponse response=new StringResponse();
        response.setMessage("Update comment Successfully");
        response.setResponseStatus(ResponseCode.SUCCESS.name());
        response.setResponseCode(ResponseCode.SUCCESS.getCode());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<?> deleteCommemt(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
        StringResponse response=new StringResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.name(),"Delete comment Successfully");
        return ResponseEntity.ok(response);
    }

}
