package com.example.Clinic_API.service;

import com.example.Clinic_API.entities.Appointment;
import com.example.Clinic_API.entities.User;
import com.example.Clinic_API.repository.AppointmentRepository;
import com.example.Clinic_API.repository.UserRepository;
import com.example.Clinic_API.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    private final CurrentUser currentUser=new CurrentUser();
    @Autowired
    private UserRepository userRepository;

    public void createAppointment(Long doctorId, List<Appointment> appointments){
        try {
            User doctor = userRepository.findById(doctorId).orElseThrow(() -> new RuntimeException("This doctor doesn't exsit"));
            currentUser.getInfoUser();
            if (currentUser.getUser() != doctor)
                throw new RuntimeException("This action is banned");
            for (Appointment a : appointments){
                a.setStatus("VACANT");
                a.setDoctor(doctor);
                appointmentRepository.save(a);
            }
        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public void updateInfoAppointment(List<Appointment> appointments){
        currentUser.getInfoUser();
        for (Appointment appointment: appointments){
            if (appointment.getDoctor()!=currentUser.getUser())
                throw new RuntimeException("This action is banned");
        }
        appointmentRepository.saveAll(appointments);
    }
}
