package com.example.Clinic_API.controller;

import com.example.Clinic_API.payload.FacultyResponse;
import com.example.Clinic_API.payload.ListFacultyResponse;
import com.example.Clinic_API.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/faculty")
public class FacultyController {

    @Autowired
    FacultyService facultyService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllFaculty(){
        ListFacultyResponse response= facultyService.getAllFaculty();
        return ResponseEntity.ok(response);
    }
}
