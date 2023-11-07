package com.example.Clinic_API.controller;

import com.example.Clinic_API.enums.ResponseCode;
import com.example.Clinic_API.payload.ChangePassRequest;
import com.example.Clinic_API.payload.StringResponse;
import com.example.Clinic_API.payload.UserUpdateRequest;
import com.example.Clinic_API.service.UserService;
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

    @PostMapping("/changePass")
    public ResponseEntity<?> changePass(@Valid @RequestBody ChangePassRequest changePassRequest){
        userService.changePass(changePassRequest);
        StringResponse response=new StringResponse();
        response.setResponseStatus(ResponseCode.SUCCESS.name());
        response.setResponseCode(ResponseCode.SUCCESS.getCode());
        response.setMessage("Change password success");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/updateAvatar/{id}")
    public ResponseEntity<?> updateAvatar(@PathVariable Long id, @RequestParam MultipartFile avatar){
        userService.updateAvatar(id,avatar);
        StringResponse response=new StringResponse();
        response.setResponseStatus(ResponseCode.SUCCESS.name());
        response.setResponseCode(ResponseCode.SUCCESS.getCode());
        response.setMessage("Update avatar user success");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/updateUser/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id,@RequestBody UserUpdateRequest userUpdateRequest){
        userService.updateUser(id,userUpdateRequest);
        StringResponse response=new StringResponse();
        response.setResponseStatus(ResponseCode.SUCCESS.name());
        response.setResponseCode(ResponseCode.SUCCESS.getCode());
        response.setMessage("Update info user success");
        return ResponseEntity.ok(response);
    }
}
