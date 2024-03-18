package com.example.Clinic_API.service;

import com.example.Clinic_API.entities.Clinic;
import com.example.Clinic_API.entities.Rate;
import com.example.Clinic_API.entities.User;
import com.example.Clinic_API.repository.ClinicRepository;
import com.example.Clinic_API.repository.RateRepository;
import com.example.Clinic_API.repository.UserRepository;
import com.example.Clinic_API.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RateService {

    @Autowired
    ClinicRepository clinicRepository;

    @Autowired
    private final CurrentUser currentUser = new CurrentUser();
    @Autowired
    private RateRepository rateRepository;
    @Autowired
    private UserRepository userRepository;

    public void rateClinic(Long clinicId, int numbStar) {
        currentUser.getInfoUser();
        Clinic clinic = clinicRepository.findById(clinicId).orElseThrow(() -> new RuntimeException("This clinic doesn't exsit"));
        if (!rateRepository.existsByClinicAndUser(clinic, currentUser.getUser())) {
            Rate rate = new Rate();
            rate.setClinic(clinic);
            currentUser.getInfoUser();
            rate.setUser(currentUser.getUser());
            rate.setNumStar(numbStar);
            rateRepository.save(rate);
        }
    }

    public void rateDoctor(Long doctorId, int numbStar) {
        currentUser.getInfoUser();
        User doctor = userRepository.findById(doctorId).orElseThrow(() -> new RuntimeException("This doctor doesn't exsit"));
        if (!rateRepository.existsByDoctorAndUser(doctor, currentUser.getUser())) {
            Rate rate = new Rate();
            rate.setDoctor(doctor);
            currentUser.getInfoUser();
            rate.setUser(currentUser.getUser());
            rate.setNumStar(numbStar);
            rateRepository.save(rate);
        }
    }

    public Double getRateClinic(Long clinicId) {
        try {
            Clinic clinic = clinicRepository.findById(clinicId).orElseThrow(() -> new RuntimeException("This clinic doesn't exsit"));
            List<Rate> rates = rateRepository.findByClinic(clinic);
            int sum = rates.stream().mapToInt(r -> r.getNumStar()).sum();
            return sum * 1.0 / rates.size();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Double getRateDoctor(Long doctorId) {
        try {
            User doctor = userRepository.findById(doctorId).orElseThrow(() -> new RuntimeException("This doctor doesn't exsit"));
            List<Rate> rates = rateRepository.findByDoctor(doctor);
            int sum = rates.stream().mapToInt(r -> r.getNumStar()).sum();
            return sum * 1.0 / rates.size();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
