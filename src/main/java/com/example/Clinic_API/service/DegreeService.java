package com.example.Clinic_API.service;

import com.example.Clinic_API.entities.Degree;
import com.example.Clinic_API.entities.User;
import com.example.Clinic_API.repository.DegreeRepository;
import com.example.Clinic_API.repository.UserRepository;
import com.example.Clinic_API.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DegreeService {

    @Autowired
    DegreeRepository degreeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private static CurrentUser currentUser;

    public List<Degree> getDegreeByClinicId(Long doctorId) {
        User doctor = userRepository.findById(doctorId).orElseThrow(() -> new RuntimeException("This doctor doesn't exsit"));
        return degreeRepository.findByDoctors(List.of(doctor));
    }

    public void addDegreeIntoDoctor(Long doctorId, List<Long> degreeIds) {
        User doctor = userRepository.findById(doctorId).orElseThrow(() -> new RuntimeException("This doctor doesn't exsit"));
        currentUser.getInfoUser();
        if (doctor != currentUser.getUser())
            throw new RuntimeException("This action is banned");
        List<Degree> degrees = degreeIds.stream().map(degreeRepository::findById).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
        doctor.setDegrees(degrees);
    }
}
