package com.example.Clinic_API.controller;

import com.example.Clinic_API.entities.User;
import com.example.Clinic_API.enums.ResponseCode;
import com.example.Clinic_API.payload.ChangePassRequest;
import com.example.Clinic_API.payload.StringResponse;
import com.example.Clinic_API.payload.UserUpdateRequest;
import com.example.Clinic_API.service.UserService;
import com.example.Clinic_API.specification.SpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping("getInfoUser/{userId}")
    public ResponseEntity<?> getInfoUserById(@PathVariable Long userId){
        User user= userService.getInfoUserById(userId);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/changePass")
    public ResponseEntity<?> changePass(@Valid @RequestBody ChangePassRequest changePassRequest){
        userService.changePass(changePassRequest);
        StringResponse response=new StringResponse();
        response.setResponseStatus(ResponseCode.SUCCESS.name());
        response.setResponseCode(ResponseCode.SUCCESS.getCode());
        response.setMessage("Change password success");
        return ResponseEntity.ok(response);
    }

    // cập nhập thông tin ảnh đại diện của người dùng
    @GetMapping("/updateAvatar/{id}")
    public ResponseEntity<?> updateAvatar(@PathVariable Long id, @RequestParam MultipartFile avatar){
        userService.updateAvatar(id,avatar);
        StringResponse response=new StringResponse();
        response.setResponseStatus(ResponseCode.SUCCESS.name());
        response.setResponseCode(ResponseCode.SUCCESS.getCode());
        response.setMessage("Update avatar user Successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/updateInfoUser/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id,@RequestBody UserUpdateRequest userUpdateRequest){
        userService.updateInfoUser(id,userUpdateRequest);
        StringResponse response=new StringResponse();
        response.setResponseStatus(ResponseCode.SUCCESS.name());
        response.setResponseCode(ResponseCode.SUCCESS.getCode());
        response.setMessage("Update info user success");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("deleteUser/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long userId){
        userService.deleteUserById(userId);
        StringResponse response=new StringResponse();
        response.setMessage("Delete User Success");
        response.setResponseCode(ResponseCode.SUCCESS.getCode());
        response.setResponseStatus(ResponseCode.SUCCESS.name());
        return ResponseEntity.ok(response);
    }
}
