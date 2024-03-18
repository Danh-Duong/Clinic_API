package com.example.Clinic_API.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.Clinic_API.entities.*;
import com.example.Clinic_API.enums.OperationEnum;
import com.example.Clinic_API.payload.*;
import com.example.Clinic_API.repository.*;
import com.example.Clinic_API.security.CurrentUser;
import com.example.Clinic_API.specification.SpecificationBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ClinicService {

    @Autowired
    ClinicRepository clinicRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    AttachmentTypeRepository attachmentTypeRepository;

    @Autowired
    FacultyRepository facultyRepository;

    @Autowired
    CurrentUser currentUser=new CurrentUser();

    @Autowired
    UserRepository userRepository;

//    @Autowired
//    DistrictRepository districtRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    Cloudinary cloudinary;

    @Autowired
    ModelMapper modelMapper;

    //trả về danh sách các bệnh viện filters
    public List<ClinicResponse> getAllClinics(Integer limit,Integer page,Integer provinceId, Integer districtId,Long facultyId, String name){
        try{
        SpecificationBuilder builder=new SpecificationBuilder();
        if (name!=null)
            builder.with("vietName", OperationEnum.LIKE,name);
//        if (provinceId!=null)
//            builder.with("district.id.province.id",OperationEnum.EQUALS, String.valueOf(provinceId));
        if (districtId!=null)
            builder.with("district.id", OperationEnum.EQUALS, String.valueOf(districtId));

        Specification specification=builder.build();
        List<Clinic> clinics= clinicRepository.findAll(specification, PageRequest.of(page-1,limit.intValue())).getContent();
//        List<Clinic> clinics=clinicRepository.findAll();
//        System.out.println("Danh: " + clinics.size());
            List<ClinicResponse> responses=new ArrayList<>();
        for (Clinic clinic: clinics){
            ClinicResponse clinicResponse=modelMapper.map(clinic, ClinicResponse.class);
            // lấy ảnh đại diện
//                Attachment avatarAttachment= attachmentRepository.findByClinicIdAndAttachmentTypeCode(clinic.getId(),"AVATAR");
            // khi tạo bình viện thì sẽ có ảnh đại diện mặc định
//                String avatarUrl=avatarAttachment.getUrl();
//                clinicResponse.setAvatarUrl(avatarUrl);
            List<FacultyResponse> facultyResponses=new ArrayList<>();
            for (Faculty faculty: clinic.getFaculties())
                facultyResponses.add(modelMapper.map(faculty, FacultyResponse.class));
            clinicResponse.setFacultyResponses(facultyResponses);
            responses.add(clinicResponse);
        }
            return responses;}
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    // lấy danh sách bệnh viện có tổng lịch đặt cao nhất
    public List<Clinic> getBestClinic(){
        // lấy tổng các lịch đặt của các bác sĩ trong bệnh viện đó.
        List<User> doctors =  userRepository
    }

    // trả về thông tin chi tiết của bệnh viện
    public ClinicDetailResponse getClinicById(Long clinicId){
        Clinic clinic=clinicRepository.findById(clinicId).orElseThrow(() -> new RuntimeException("This clinic does not exist"));
        ClinicDetailResponse clinicDetailResponse=new ClinicDetailResponse();
        BeanUtils.copyProperties(clinic,clinicDetailResponse);
        for (Faculty f: clinic.getFaculties()){
            clinicDetailResponse.getFacultieNames().add(f.getName());
        }
        return clinicDetailResponse;
    }

    public void createClinic(ClinicRequest request, MultipartFile file){
        try{
        // kiểm tra tại vị trí này đã tồn tại bệnh viện chưa
        if (clinicRepository.findByAddress(request.getAddress())!=null)
            throw new RuntimeException("This address of clinic is already exsit");
        // 1 bác sĩ chỉ có thể tạo được 1 phòng khám
//        if (clinicRepository.findByUserCreate(currentUser.getUser())!=null)
//            throw new RuntimeException("One doctor only crate one clinic");
        Clinic clinic= new Clinic();
        clinic= modelMapper.map(request, Clinic.class);
        List<Faculty> faculties=new ArrayList<>();

        // lưu thông tin của các khoa
        for (String f: request.getFacultyNames()){
            Faculty faculty = facultyRepository.findByName(f);
            faculties.add(faculty);
        }

        // đối với many to many thì chỉ cần set giá trị list cho 1 cái thôi
        // nên chỉ cần lưu list faculty vào clinic là được
        clinic.setFaculties(faculties);

        String avatarUrl= cloudinary.uploader().upload(file.getBytes(),ObjectUtils.emptyMap()).get("secure_url").toString();
        Attachment attachment=new Attachment();
        attachment.setClinic(clinic);
        attachment.setAttachmentType(attachmentTypeRepository.findByCode("AVATAR"));
        attachment.setUrl(avatarUrl);

//            List<String> address= List.of(request.getAddress().split(","));
//            clinic.setDistrict(districtRepository.findByNameLike(address.get(address.size()-1)));
        // lưu thông tin của người tạo bệnh viện
        User user=currentUser.getInfoUser().getUser();
        clinic.setUserCreate(user);
        clinic.setUsers(Arrays.asList(user));
        clinicRepository.save(clinic);

        // lưu thông tin của attachment
        attachmentRepository.save(attachment);

    }
        catch (Exception e){
//            throw new RuntimeException(e.getMessage());
            e.printStackTrace();
        }

    }

    public void updateClinic(Long clinicId,ClinicRequest clinicRequest){
        try{
        Clinic clinic=clinicRepository.findById(clinicId).orElseThrow(()-> new RuntimeException("This clinic doesn't exsit"));
       currentUser.getInfoUser();
        // không có quyền
        if (clinic.getUserCreate()!=currentUser.getUser())
            throw new RuntimeException("This action is banned");
//        BeanUtils.copyProperties(clinicRequest, clinic);
        if (clinicRequest.getVietName()!=null && clinicRepository.findByVietName(clinicRequest.getVietName())!=null)
//            if (clinic.getVietName()!=clinicRequest.getVietName() && clinicRepository.findByAddress(clinic.getAddress())!=null)
                throw new RuntimeException("This name of clinic already exists");
//        else
//            clinic.setVietName(clinicRequest.getVietName());
        if (clinicRequest.getAddress()!=null && clinicRepository.findByAddress(clinicRequest.getAddress())!=null)
            throw new RuntimeException("This address of clinic already exists");
//        else
//            clinic.setAddress(clinicRequest.getAddress());
//        if (clinicRequest.getPhone()!=null)
//            clinic.setPhone(clinicRequest.getPhone());
//        if (clinicRequest.getEmail()!=null)
//            clinic.setEmail(clinicRequest.getEmail());
//        if (clinicRequest.getUrlInfo()!=null)
//            clinic.setUrlInfo(clinicRequest.getUrlInfo());
        clinic = modelMapper.map(clinicRequest, Clinic.class);
        List<Faculty> newFaculties=new ArrayList<>();
        if (clinicRequest.getFacultyNames().size()>0){
//            // xóa tất cả những khoa hiện tại của bệnh viện
//            clinic.getFaculties().clear();
            for (String s : clinicRequest.getFacultyNames()){
                Faculty fac=null;
                fac=facultyRepository.findByName(s);
                if (fac==null){
                    fac.setName(s);
                    facultyRepository.save(fac);
                }
                newFaculties.add(fac);
            }
            clinic.setFaculties(newFaculties);
        }
        clinicRepository.save(clinic);}
        catch (Exception e){
            e.printStackTrace();
        }
    }
//
//    public void updateAvatarClinic(Long clinicId, MultipartFile file){
//        try{
//            // lưu ảnh vào attachment
//            Clinic clinic=clinicRepository.findById(clinicId).orElseThrow(() -> new RuntimeException("This clinic is exsit"));
//            String oldAvatarUrl=clinic.getUrlAvatar();
//            cloudinary.uploader().destroy(FileService.getNameImage(oldAvatarUrl),ObjectUtils.emptyMap());
//            clinic.setUrlAvatar(cloudinary.uploader().upload(file.getBytes(),ObjectUtils.emptyMap()).get("secure_url").toString());
//            clinicRepository.save(clinic);
//        }
//        catch (Exception e){
//            throw new RuntimeException(e.getMessage());
//        }
//
//    }
//
    public void deleteClinic(Long clinicId){
        try{
            Clinic clinic=clinicRepository.findById(clinicId).orElseThrow(()-> new RuntimeException("This clinic doesn't exsit"));
            currentUser.getInfoUser();
            // chỉ có người tạo bệnh viện và admin mới được quyền xóa
            if (!(clinic.getUserCreate()==currentUser.getUser() || currentUser.getIsAdmin()))
                throw new RuntimeException("This action is banned");
            // đối với các quan hệ many to many thì sẽ delete trước toàn bộ dữ liệu liên quan
            // dùng cascade ALL
            clinicRepository.delete(clinic);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
//
    public void addDoctorIntoClinic(Long clinicId, String email){
        Clinic clinic=clinicRepository.findById(clinicId).orElseThrow(() -> new RuntimeException("This clinic doesn't exsit"));
        User doctor=userRepository.findByUsername(email);
        if (doctor==null)
            throw new RuntimeException("This added doctor doesn't exsit");
        if (!doctor.getRoles().contains(roleRepository.findByCode("ROLE_DOCTOR")))
            throw new RuntimeException("This user isn't doctor");
        if (clinic.getUsers().contains(doctor))
            throw new RuntimeException("This doctor is already in this clinic");
        // kiểm tra xem user có tồn tại ở clinic khác không
        // trường hợp bác sĩ đi khám bệnh
//        if (doctor.getClinics().size()>0)
//            throw new RuntimeException("This doctor belongs to another clinic");
        currentUser.getInfoUser();
        if (currentUser.getUser()!=clinic.getUserCreate())
            throw new RuntimeException("This action is banned. You must a doctor create clinic");
        clinic.getUsers().add(doctor);
        clinicRepository.save(clinic);
    }
}
