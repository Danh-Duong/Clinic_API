package com.example.Clinic_API.service;

import com.example.Clinic_API.entities.User;
import com.example.Clinic_API.payload.BestDocterResponse;
import com.example.Clinic_API.repository.BookingRepository;
import com.example.Clinic_API.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {

    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    private UserRepository userRepository;

    public List<BestDocterResponse> getBestDoctor(Integer top){
        try{
        List<BestDocterResponse> responses = new ArrayList<>();
        List<Long> doctorIds= bookingRepository.getBestDoctor();
        for (Long id: doctorIds){
            User doctor= userRepository.findById(id).get();
            BestDocterResponse bd = new BestDocterResponse();
            bd.setName(doctor.getName());
            bd.setAvatarUrl(doctor.getAvatarUrl());
            bd.setNumbBooking(bookingRepository.countBookingDoctor(doctor.getId()));
            bd.setNumbRate(0);
            responses.add(bd);
        }
        return responses;}
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
