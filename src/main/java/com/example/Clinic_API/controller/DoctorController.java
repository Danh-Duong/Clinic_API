package com.example.Clinic_API.controller;

import com.example.Clinic_API.enums.ResponseCode;
import com.example.Clinic_API.payload.StringResponse;
import com.example.Clinic_API.service.DoctorService;
import com.example.Clinic_API.specification.SpecificationBuilder;
import com.example.Clinic_API.specification.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/doctor")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    // lấy danh sách bác sĩ tìm kiếm theo
    // danh sách được sắp xếp theo thông tin số lần đặt lịch giảm dần
//    @GetMapping("getListDoctors")
//    public ResponseEntity<?> getListDoctors(
//            @RequestParam(required = false) String nameDoctor,
//            @RequestParam(required = false) Long provinceId,
//            @RequestParam(required = false) Long districtId,
//            @RequestParam(required = false) Long facultyId,
//            @RequestParam(required = false) Long clinicId,
//            @RequestParam(required = false, defaultValue = "1") Integer page,
//            @RequestParam(required = false, defaultValue = "10") Integer limit
//    ){
//
//        UserSpecification spec = new UserSpecification();
//        if (nameDoctor!=null)
//            spec.likeName(nameDoctor);
////      thông tin về địa chỉ của bác sĩ được tham chiếu theo địa chỉ của phòng khám
//        if (provinceId!=null)
//            spec.equalProvinceId(provinceId);
//        if (districtId!=null)
//
//
//
//        return ResponseEntity.ok(limit);
//    }

    // lấy ra danh sách 3 bác sĩ có số lượng đặt nhiều nhất
    @GetMapping("/getBestDoctor")
    public ResponseEntity<?> getBestDoctor(@RequestParam(required = false, defaultValue = "3") Integer top){
        return ResponseEntity.ok(doctorService.getBestDoctor(top));
    }


    // gửi yêu cầu để được thêm vào clinic



}
