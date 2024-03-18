package com.example.Clinic_API.controller;

import com.example.Clinic_API.enums.ResponseCode;
import com.example.Clinic_API.payload.StringResponse;
import com.example.Clinic_API.service.DegreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/degree")
public class DegreeController {

    @Autowired
    DegreeService degreeService;

    @GetMapping("/{doctorId}")
    public ResponseEntity<?> getDegreeByDoctor(@PathVariable Long doctorId){
        return ResponseEntity.ok(degreeService.getDegreeByClinicId(doctorId));
    }

    @PostMapping("add/{doctorId}")
    public ResponseEntity<?> addDegreeIntoDoctor(@PathVariable Long doctorId, List<Long> degreeIds){
        StringResponse response = new StringResponse();
        response.setResponseStatus(ResponseCode.SUCCESS.name());
        response.setResponseCode(ResponseCode.SUCCESS.getCode());
        response.setMessage("Add Degree into Doctor Successfully");
        return ResponseEntity.ok(response);
    }
}
