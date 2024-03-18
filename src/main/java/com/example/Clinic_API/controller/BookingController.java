package com.example.Clinic_API.controller;

import com.example.Clinic_API.enums.ResponseCode;
import com.example.Clinic_API.payload.BookingRequest;
import com.example.Clinic_API.payload.StringResponse;
import com.example.Clinic_API.service.BookingService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    BookingService bookingService;

    // user đặt lịch khám bệnh
    @PostMapping("bookingDoctor")
    public ResponseEntity<?> bookingDoctor(@RequestBody @Valid BookingRequest request){
        bookingService.createBookking(request);
        StringResponse response= new StringResponse(ResponseCode.RESPONSING.getCode(),ResponseCode.RESPONSING.name(), "Vui lòng chờ phản hồi từ bác sĩ!");
        return ResponseEntity.ok(response);
    }

//    bác sĩ xác nhận thông tin đặt lịch
    @PutMapping("/confirmBooking")
    public ResponseEntity<?> confirmBooking(@RequestParam Long bookingId){
        bookingService.confirmBooking(bookingId);
        return ResponseEntity.ok("Sending Response Successfully");
    }

    // xem lịch khám bệnh
    @GetMapping("/getBooking")
    public ResponseEntity<?> getAppointmentsByStatus(@RequestParam(defaultValue = "10") int limit,
                                                        @RequestParam(defaultValue = "1") int page,
                                                        @RequestParam String status){
        return ResponseEntity.ok(bookingService.getAppointmentsByStatus(limit, page, status));
    }


}
