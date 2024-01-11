package com.example.Clinic_API.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.Clinic_API.entities.*;
import com.example.Clinic_API.enums.OperationEnum;
import com.example.Clinic_API.payload.ClinicDetailResponse;
import com.example.Clinic_API.payload.ClinicRequest;
import com.example.Clinic_API.payload.ClinicResponse;
import com.example.Clinic_API.payload.FacultyResponse;
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
import java.util.List;

@Service
public class ClinicService {

    @Autowired
    ClinicRepository clinicRepository;

    @Autowired
    AttachmentRepository attachmentRepository;
//
//    @Autowired
//    AttachmentTypeRepository attachmentTypeRepository;

    @Autowired
    FacultyRepository facultyRepository;

    @Autowired
    final CurrentUser currentUser=new CurrentUser();

    @Autowired
    UserRepository userRepository;

    @Autowired
    DistrictRepository districtRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    Cloudinary cloudinary;

    @Autowired
    ModelMapper modelMapper;

//    public List<ClinicResponse> getAllClinics(Integer limit,Integer page,Integer provinceId, Integer districtId, String name){
//        try{
//            SpecificationBuilder builder=new SpecificationBuilder();
//            if (name!=null)
//                builder.with("vietname", OperationEnum.LIKE,name);
//            if (provinceId!=null)
//                builder.with("district.id.province.id",OperationEnum.EQUALS, String.valueOf(provinceId));
//            if (districtId!=null)
//                builder.with("district.id", OperationEnum.EQUALS, String.valueOf(districtId));
//
//            Specification specification=builder.build();
//            List<Clinic> clinics= clinicRepository.findAll(specification, PageRequest.of(page-1,limit.intValue())).getContent();
//            List<ClinicResponse> responses=new ArrayList<>();
//            for (Clinic clinic: clinics){
//                ClinicResponse clinicResponse=modelMapper.map(clinic, ClinicResponse.class);
//                // lấy ảnh đại diện
////                Attachment avatarAttachment= attachmentRepository.findByClinicIdAndAttachmentTypeCode(clinic.getId(),"AVATAR");
//                // khi tạo bình viện thì sẽ có ảnh đại diện mặc định
//                String avatarUrl=avatarAttachment.getUrl();
//                clinicResponse.setAvatarUrl(avatarUrl);
//                List<FacultyResponse> facultyResponses=new ArrayList<>();
//                for (Faculty faculty: clinic.getFaculties())
//                    facultyResponses.add(modelMapper.map(faculty, FacultyResponse.class));
//                clinicResponse.setFacultyResponses(facultyResponses);
//            }
//            return responses;
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return null;
//    }

    // trả về thông tin chi tiết của bệnh viện
//    public ClinicDetailResponse getClinicById(Long clinicId){
//        Clinic clinic=clinicRepository.findById(clinicId).orElseThrow(() -> new RuntimeException("This clinic is none-exsit"));
//        ClinicDetailResponse clinicDetailResponse=new ClinicDetailResponse();
//        BeanUtils.copyProperties(clinic,clinicDetailResponse);
//        for (Faculty f: clinic.getFaculties()){
//            clinicDetailResponse.getFacultieNames().add(f.getName());
//        }
//        clinicDetailResponse.setClinicType(clinic.getClinicType().getName());
//        return clinicDetailResponse;
//    }
//
//
//    public void createClinic(ClinicRequest clinicRequest){
//        try{
//            // kiểm tra tại vị trí này đã tồn tại bệnh viện chưa
//            if (clinicRepository.findByAddress(clinicRequest.getAddress())!=null)
//                throw new RuntimeException("This address is already exsit");
//            Clinic clinic= new Clinic();
//            BeanUtils.copyProperties(clinicRequest,clinic);
//            clinic.setClinicType(clinicTypeRepository.findById(clinicRequest.getClinicTypeCode()).get());
//            List<Faculty> faculties=new ArrayList<>();
//
//            // lưu thông tin của các khoa
//            for (String f: clinicRequest.getFacultyNames()){
//                // kiểm tra thông tin khoa đã tồn tại trên hệ thống chưa
//                Faculty faculty;
//                if (facultyRepository.findByName(f)==null){
//                    faculty=new Faculty();
//                    faculty.setName(f);
//                    facultyRepository.save(faculty);
//                    faculties.add(faculty);
//                }
//                else{
//                    faculty=facultyRepository.findByName(f);
//                    faculties.add(faculty);
//                }
//            }
//            List<String> add= List.of(clinicRequest.getAddress().split(","));
//
//
//            clinic.setUrlAvatar(cloudinary.uploader().upload(clinicRequest.getFile().getBytes(), ObjectUtils.emptyMap()).get("secure_url").toString());
////            clinic.setDistrict(districtRepository.findByName(add.get(2)));
//            // mẫu dữ liệu: 87 cao thắng, thanh binh, hai chau, da nang
//            // set giá trị district id mỗi khi tạo
//            // clinic.setDistrictId(districtRepository.findByName(add.get(add.size()-2)).getId());
//            // set giá trị clinic type id mỗi khi tạo
//
//            // đối với many to many thì chỉ cần set giá trị list cho 1 cái thôi
//            // nên chỉ cần lưu list faculty vào clinic là được
//            clinic.setFaculties(faculties);
//
//            // lưu thông tin của người tạo bệnh viện
//            currentUser.getInfoUser();
//            clinic.setUserCreate(currentUser.getUser());
//            clinic.setUsers(Arrays.asList(currentUser.getUser()));
//            clinicRepository.save(clinic);
//
//            // cái này nhằm mục đích sử dụng trong tìm kiếm
//            clinic.setClinicTypeId(clinicRequest.getClinicTypeCode());
//            ClinicType clinicType=clinicTypeRepository.findById(clinicRequest.getClinicTypeCode()).get();
//            clinicType.getClinics().add(clinic);
//            clinicTypeRepository.save(clinicType);
//        }
//        catch (Exception e){
//            throw new RuntimeException(e.getMessage());
//        }
//    }
//
//    public void updateClinic(Long clinicId,ClinicRequest clinicRequest){
//        Clinic clinic=clinicRepository.findById(clinicId).orElseThrow(()-> new RuntimeException("This clinic is non-exsit"));
//        currentUser.getInfoUser();
//        // không có quyền
//        if (clinic.getUserCreate()!=currentUser.getUser())
//            throw new RuntimeException("This action is banned");
//        BeanUtils.copyProperties(clinicRequest, clinic);
//        if (clinicRequest.getVietName()!=null)
//            if (clinic.getVietName()!=clinicRequest.getVietName() && clinicRepository.findByAddress(clinic.getAddress())!=null)
//                throw new RuntimeException("This name is exsit");
//            clinic.setVietName(clinicRequest.getVietName());
//        if (clinicRequest.getAddress()!=null)
//            clinic.setAddress(clinicRequest.getAddress());
//        if (clinicRequest.getPhone()!=null)
//            clinic.setPhone(clinicRequest.getPhone());
//        if (clinicRequest.getEmail()!=null)
//            clinic.setEmail(clinicRequest.getEmail());
//        if (clinicRequest.getUrlInfo()!=null)
//            clinic.setUrlInfo(clinicRequest.getUrlInfo());
//        if (clinicRequest.getFacultyNames().size()>0){
//            // xóa tất cả những khoa hiện tại của bệnh viện
//            clinic.getFaculties().clear();
//            for (String s : clinicRequest.getFacultyNames()){
//                Faculty f=facultyRepository.findByName(s);
//                if (f==null){
//                    Faculty newF=new Faculty();
//                    newF.setName(s);
//                    facultyRepository.save(newF);
//                    clinic.getFaculties().add(newF);
//                }
//                else
//                    clinic.getFaculties().add(f);
//            }
//        }
//        clinicRepository.save(clinic);
//    }
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
//    public void deleteClinic(Long clinicId){
//        try{
//            Clinic clinic=clinicRepository.findById(clinicId).orElseThrow(()-> new RuntimeException("This clinic is non_exsit"));
//            currentUser.getInfoUser();
//            // chỉ có người tạo bệnh viện và admin mới được quyền xóa
//            if (!(clinic.getUserCreate()==currentUser.getUser() || currentUser.getIsAdmin()))
//                throw new RuntimeException("This action is banned");
//            // đối với các quan hệ many to many thì sẽ delete trước toàn bộ code
//            clinic.getFaculties().clear();
//            clinicRepository.delete(clinic);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public void addDoctorIntoClinic(Long clinicId, String email){
//        Clinic clinic=clinicRepository.findById(clinicId).orElseThrow(() -> new RuntimeException("This clinic is none-exsit"));
//        User user=userRepository.findByUsername(email);
//        if (user==null)
//            throw new RuntimeException("This doctor is none-exsit");
//        if (!user.getRoles().contains(roleRepository.findByCode("ROLE_DOCTOR")))
//            throw new RuntimeException("This user isn't doctor");
//        if (clinic.getUsers().contains(user))
//            throw new RuntimeException("This doctor is already in this clinic");
//        // kiểm tra xem user có tồn tại ở clinic khác không
//        if (user.getClinics().size()>0)
//            throw new RuntimeException("This doctor belongs to another clinic");
//        currentUser.getInfoUser();
//        if (currentUser.getUser()!=clinic.getUserCreate())
//            throw new RuntimeException("This action is banned. You must a doctor create clinic");
//        clinic.getUsers().add(user);
//        clinicRepository.save(clinic);
//    }
}
