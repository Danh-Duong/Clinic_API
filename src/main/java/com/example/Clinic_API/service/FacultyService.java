package com.example.Clinic_API.service;

import com.example.Clinic_API.entities.Faculty;
import com.example.Clinic_API.payload.ListFacultyResponse;
import com.example.Clinic_API.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyService {

    @Autowired
    FacultyRepository facultyRepository;

    public ListFacultyResponse getAllFaculty(){
        ListFacultyResponse faculties= new ListFacultyResponse();
        faculties.setFaculties(facultyRepository.findAll());
        return faculties;
    }
}
