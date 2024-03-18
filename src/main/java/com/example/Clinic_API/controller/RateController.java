package com.example.Clinic_API.controller;

import com.example.Clinic_API.enums.ResponseCode;
import com.example.Clinic_API.payload.RateResponse;
import com.example.Clinic_API.payload.StringResponse;
import com.example.Clinic_API.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/rate")
public class RateController {

    @Autowired
    RateService rateService;

//    Lấy đánh giá trung bình của 1 clinic
    @GetMapping("clinic")
    public ResponseEntity<?> getRateClinic(@RequestParam Long clinicId){
        RateResponse response=new RateResponse();
        response.setNumbRate(rateService.getRateClinic(clinicId));
        response.setResponseCode(ResponseCode.SUCCESS.getCode());
        response.setResponseStatus(ResponseCode.SUCCESS.name());
        return ResponseEntity.ok(response);
    }

    // tạo đánh giá clinic
    @PostMapping("clinic/create")
    public ResponseEntity<?> createRateClinic(@RequestParam Long clinicId, @RequestParam int numStar){
        rateService.rateClinic(clinicId, numStar);
        StringResponse response= new StringResponse();
        response.setResponseCode(ResponseCode.SUCCESS.getCode());
        response.setResponseStatus(ResponseCode.SUCCESS.name());
        response.setMessage("Rate clinic Successfully");
        return ResponseEntity.ok(response);
    }


    // lấy đánh giá bác sĩ
    @GetMapping("doctor")
    public ResponseEntity<?> getRateDoctor(@RequestParam Long doctorId){
        RateResponse response=new RateResponse();
        response.setNumbRate(rateService.getRateDoctor(doctorId));
        response.setResponseCode(ResponseCode.SUCCESS.getCode());
        response.setResponseStatus(ResponseCode.SUCCESS.name());
        return ResponseEntity.ok(response);
    }

    @PostMapping("doctor/create")
    public ResponseEntity<?> createRateDoctor(@RequestParam Long doctorId, @RequestParam int numStar){
        rateService.rateDoctor(doctorId, numStar);
        StringResponse response= new StringResponse();
        response.setResponseCode(ResponseCode.SUCCESS.getCode());
        response.setResponseStatus(ResponseCode.SUCCESS.name());
        response.setMessage("Rate doctor Successfully");
        return ResponseEntity.ok(response);
    }

}
