package com.example.Clinic_API.controller;

import com.example.Clinic_API.enums.ResponseCode;
import com.example.Clinic_API.payload.ClinicRequest;
import com.example.Clinic_API.payload.StringResponse;
import com.example.Clinic_API.service.ClinicService;
import jdk.jfr.Frequency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/clinic")
public class ClinicController {

    @Autowired
    ClinicService clinicService;

    @GetMapping("/getClinics")
    public ResponseEntity<?> getAllClinics(@RequestParam(required = false, defaultValue = "10") Integer limit,
                                           @RequestParam(required = false, defaultValue = "1") Integer page,
                                           @RequestParam(required = false) Integer provinceId,
                                           @RequestParam(required = false) Integer districtId,
                                           @RequestParam(required = false) Long facultyId,
                                           @RequestParam(required = false) String name){
        return ResponseEntity.ok(clinicService.getAllClinics(limit,page,provinceId, districtId, facultyId,name));
    }

    // lấy thông tin chi tiết của clinic
    @GetMapping("/{clinicId}")
    public ResponseEntity<?> getClinic(@PathVariable Long clinicId){
        return ResponseEntity.ok(clinicService.getClinicById(clinicId));
    }

    @PostMapping(value = "/create", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @PreAuthorize("hasAnyRole('ROLE_DOCTOR')")
    // ảnh ở đây là ảnh đại diện của bệnh viện
    public ResponseEntity<?> createClinic(@ModelAttribute ClinicRequest request){
        clinicService.createClinic(request,request.getFile());
        StringResponse response=new StringResponse();
        response.setMessage("Create Clinic Successfully");
        response.setResponseCode(ResponseCode.SUCCESS.getCode());
        response.setResponseStatus(ResponseCode.SUCCESS.name());
        return ResponseEntity.ok(response);
    }

    // cập nhập thông tin của clinic
//    @PreAuthorize("hasAnyRole('ROLE_DOCTOR')")
//    @PutMapping("update/{clinicId}")
//    public ResponseEntity<?> updateClinic(@RequestBody ClinicRequest clinicRequest, @PathVariable Long clinicId){
//        clinicService.updateClinic(clinicId, clinicRequest);
//        StringResponse response=new StringResponse();
//        response.setMessage("Update Clinic success");
//        response.setResponseCode(ResponseCode.SUCCESS.getCode());
//        response.setResponseStatus(ResponseCode.SUCCESS.name());
//        return ResponseEntity.ok(response);
//    }
//
//    // cập nhập ảnh đại diện của bệnh viện
//    @PreAuthorize("hasAnyRole('ROLE_DOCTOR')")
//    @PutMapping("updateAvatar/{clinicId}")
//    public ResponseEntity<?> updateAvatarClinic(@RequestParam Long clinicId, @RequestParam MultipartFile file){
//        clinicService.updateAvatarClinic(clinicId, file);
//        StringResponse response=new StringResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.name(), "Update avatar clinic Success");
//        return ResponseEntity.ok(response);
//    }
//
//    // dùng cascade=Cascade.ALL để có thể xóa các dữ liệu liên quan
//    @PreAuthorize("hasAnyRole('ROLE_DOCTOR', 'ROLE_ADMIN')")
//    @DeleteMapping("delete/{clinicId}")
//    public ResponseEntity<?> deleteClinic(@PathVariable Long clinicId){
//        clinicService.deleteClinic(clinicId);
//        StringResponse response=new StringResponse();
//        response.setMessage("Delete Clinic success");
//        response.setResponseCode(ResponseCode.SUCCESS.getCode());
//        response.setResponseStatus(ResponseCode.SUCCESS.name());
//        return ResponseEntity.ok(response);
//    }

    // thêm bác sĩ vào phòng khám bởi chủ phòng khám
//    @PreAuthorize("hasAnyRole('ROLE_DOCTOR')")
//    @PostMapping("addDoctor")
//    public ResponseEntity<?> addDoctorIntoClinic(@RequestParam Long clinicId,
//                                                 @RequestParam String username,
//                                                 @RequestParam String email){
//        clinicService.addDoctorIntoClinic(clinicId,username, email);
//        StringResponse response=new StringResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.name(), "Add doctor into clinic success");
//        return ResponseEntity.ok(response);
//    }



}
