package com.example.Clinic_API.controller;

import com.example.Clinic_API.entities.Appointment;
import com.example.Clinic_API.payload.StringResponse;
import com.example.Clinic_API.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/appointment")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

//     thêm lịch khám bệnh của bác sĩ
    @PostMapping("")
    public ResponseEntity<?> createAppointment(@RequestParam Long doctorId, @RequestParam List<Appointment> appointments){
        appointmentService.createAppointment(doctorId, appointments);
        return ResponseEntity.ok(new StringResponse("Create Appointments Successfully"));
    }

    // cập nhập trạng thái của lịch sẵn có
    @PutMapping("")
    public ResponseEntity<?> updateInfoAppointment(@PathVariable List<Appointment> appointments){
        appointmentService.updateInfoAppointment(appointments);
        return ResponseEntity.ok(new StringResponse("Update Status Appointment Successfully"));
    }

    // xóa appointment
}
