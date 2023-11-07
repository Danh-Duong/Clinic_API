package com.example.Clinic_API.convert;

import com.example.Clinic_API.entities.User;
import com.example.Clinic_API.payload.SignupRequest;
import org.springframework.beans.BeanUtils;

public class UserConvert {

    public static User dtoToEntity(SignupRequest signupRequest){
        User user=new User();
        BeanUtils.copyProperties(signupRequest, user);
        return user;
    }
}
