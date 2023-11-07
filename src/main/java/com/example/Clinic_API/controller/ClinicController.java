package com.example.Clinic_API.controller;

import com.example.Clinic_API.enums.ResponseCode;
import com.example.Clinic_API.payload.ClinicRequest;
import com.example.Clinic_API.payload.StringResponse;
import com.example.Clinic_API.service.ClinicService;
import jdk.jfr.Frequency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

    // lấy danh sách phòng khám trên cả nước
    // dữ liệu Parameter là Object
    // đây chỉ là lấy ra danh sách còn đi sâu vô là chi tiết
    @GetMapping("/getClinics")
    public ResponseEntity<?> getAllClinics(@RequestParam(required = false, defaultValue = "10") Integer limit,@RequestParam(required = false, defaultValue = "1") Integer page,@RequestParam(required = false) Integer districtId, @RequestParam(required = false) String name,@RequestParam(required = false) Long clinicTypeId){
        return ResponseEntity.ok(clinicService.getAllClinics(limit,page,districtId,name,clinicTypeId));
    }

    @GetMapping("/{clinicId}")
    public ResponseEntity<?> getClinic(@PathVariable Long clinicId){
        return ResponseEntity.ok(clinicService.getClinicById(clinicId));
    }

    // chỗ này còn có hình ảnh nữa
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ROLE_DOCTOR')")
    // ảnh ở đây là ảnh đại diện của bệnh viện
    public ResponseEntity<?> createClinic(@RequestParam String vietName,
                                          @RequestParam(required = false) String engName,
                                          @RequestParam(required = false) String code,
                                          @RequestParam String address,
                                          @RequestParam String phone,
                                          @RequestParam String email,
                                          @RequestParam String urlInfo,
                                          @RequestParam List<String> facultyNames,
                                          @RequestParam Long clinicTypeCode,
                                          @RequestParam MultipartFile file){
        clinicService.createClinic(vietName,engName,code,address,phone,email,urlInfo,facultyNames,clinicTypeCode, file);
        StringResponse response=new StringResponse();
        response.setMessage("Create Clinic success");
        response.setResponseCode(ResponseCode.SUCCESS.getCode());
        response.setResponseStatus(ResponseCode.SUCCESS.name());
        return ResponseEntity.ok(response);
    }


    @PreAuthorize("hasAnyRole('ROLE_DOCTOR')")
    @PutMapping("update/{clinicId}")
    public ResponseEntity<?> updateClinic(@RequestBody ClinicRequest clinicRequest, @PathVariable Long clinicId){
        clinicService.updateClinic(clinicId, clinicRequest);
        StringResponse response=new StringResponse();
        response.setMessage("Update Clinic success");
        response.setResponseCode(ResponseCode.SUCCESS.getCode());
        response.setResponseStatus(ResponseCode.SUCCESS.name());
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ROLE_DOCTOR')")
    @PutMapping("updateAvatar/{clinicId}")
    public ResponseEntity<?> updateAvatarClinic(@RequestParam Long clinicId, @RequestParam MultipartFile file){
        clinicService.updateAvatarClinic(clinicId, file);
        StringResponse response=new StringResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.name(), "Update avatar clinic Success");
        return ResponseEntity.ok(response);
    }

    // xem xét khi bên trong vẫn còn chứa dữ liệu
    // phát sinh ra lỗi khi xoá đối tượng
    @PreAuthorize("hasAnyRole('ROLE_DOCTOR', 'ROLE_ADMIN')")
    @DeleteMapping("delete/{clinicId}")
    public ResponseEntity<?> deleteClinic(@PathVariable Long clinicId){
        clinicService.deleteClinic(clinicId);
        StringResponse response=new StringResponse();
        response.setMessage("Delete Clinic success");
        response.setResponseCode(ResponseCode.SUCCESS.getCode());
        response.setResponseStatus(ResponseCode.SUCCESS.name());
        return ResponseEntity.ok(response);
    }

    // thêm bác sĩ vào phòng khám
    @PreAuthorize("hasAnyRole('ROLE_DOCTOR')")
    @PostMapping("addDoctor")
    public ResponseEntity<?> addDoctorIntoClinic(@RequestParam Long clinicId,@RequestParam String username){
        clinicService.addDoctorIntoClinic(clinicId,username);
        StringResponse response=new StringResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.name(), "Add doctor into clinic success");
        return ResponseEntity.ok(response);
    }
}
