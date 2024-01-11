package com.example.Clinic_API.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/doctor")
public class DoctorController {

    // lấy danh sách bác sĩ tìm kiếm theo
    // danh sách được sắp xếp theo thông tin số lần đặt lịch giảm dần
//    @GetMapping("getListDoctors")
//    public ResponseEntity<?> getListDoctors(
//            @RequestParam(required = false) String nameDoctor,
//            @RequestParam(required = false) String provinceId,
//            @RequestParam(required = false) String districtId,
//            @RequestParam(required = false) String facultyId,
//            @RequestParam(required = false) String clinicId,
//            @RequestParam(required = false, defaultValue = "1") Integer page,
//            @RequestParam(required = false, defaultValue = "10") Integer limit
//    ){
//        SpecificationBuilder builder=new SpecificationBuilder();
//        if (nameDoctor!=null)
//            builder.with();
//        return ResponseEntity.ok(limit);
//    }

    // duyệt bác sĩ để vào phòng khám của bác sĩ tạo phòng khám

    // lấy ra danh sách 3 bác sĩ có lịch đặt nhiều nhất

    // gửi yêu cầu để được thêm vào clinic

    // đánh giá bác sĩ

    // quản lý phòng khám.


}
