package com.example.Clinic_API.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.Clinic_API.entities.*;
import com.example.Clinic_API.payload.ClinicDetailResponse;
import com.example.Clinic_API.payload.ClinicRequest;
import com.example.Clinic_API.payload.ClinicResponse;
import com.example.Clinic_API.payload.DoctorInfoRequest;
import com.example.Clinic_API.repository.*;
import com.example.Clinic_API.security.CurrentUser;
import com.example.Clinic_API.specification.ClinicSpecificationBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ClinicService {

    @Autowired
    ClinicRepository clinicRepository;

//    @Autowired
//    AttachmentRepository attachmentRepository;
//
//    @Autowired
//    AttachmentTypeRepository attachmentTypeRepository;

    @Autowired
    ClinicTypeRepository clinicTypeRepository;

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

    public List<ClinicResponse> getAllClinics(Integer limit,Integer page, Integer districtId, String name, Long clinicTypeId){
        try{
            ClinicSpecificationBuilder builder=new ClinicSpecificationBuilder();
            if (name!=null)
                builder.with("vietName",":",name);
            // tìm kiếm theo quận huyện
            if (districtId!=null)
                builder.with("districtId",":", districtId);
            if (clinicTypeId!=null)
                builder.with("clinicTypeId",":", clinicTypeId);

            Specification<Clinic> specification=builder.build();

            List<ClinicResponse> responses=new ArrayList<>();
            List<Clinic> clinics= clinicRepository.findAll(specification, PageRequest.of(page-1,limit.intValue())).getContent();
            for (Clinic clinic: clinics){
                responses.add(new ClinicResponse(clinic.getVietName(),clinic.getAddress(),clinic.getUrlAvatar()));
            }
            return responses;
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    // trả về thông tin chi tiết của bệnh viện
    public ClinicDetailResponse getClinicById(Long clinicId){
        Clinic clinic=clinicRepository.findById(clinicId).orElseThrow(() -> new RuntimeException("This clinic is none-exsit"));
        ClinicDetailResponse clinicDetailResponse=new ClinicDetailResponse();
        BeanUtils.copyProperties(clinic,clinicDetailResponse);
        for (Faculty f: clinic.getFaculties()){
            clinicDetailResponse.getFacultieNames().add(f.getName());
        }
        clinicDetailResponse.setClinicType(clinic.getClinicType().getName());
        return clinicDetailResponse;
    }

    public void createClinic(String vietName, String engName, String code, String address, String phone, String email, String urlInfo, List<String> facultyNames, Long clinicTypeCode, MultipartFile file){
        try{
            // kiểm tra tại vị trí này đã tồn tại bệnh viện chưa
            if (clinicRepository.findByAddress(address)!=null)
                throw new RuntimeException("This address is already exsit");
            Clinic clinic= new Clinic();
            clinic.setVietName(vietName);
            clinic.setEngName(engName);
            clinic.setCode(code);
            clinic.setAddress(address);
            clinic.setPhone(phone);
            clinic.setEmail(email);
            clinic.setUrlInfo(urlInfo);
            clinic.setClinicType(clinicTypeRepository.findById(clinicTypeCode).get());
            List<Faculty> faculties=new ArrayList<>();

            // lưu thông tin của các khoa
            for (String f: facultyNames){
                // kiểm tra thông tin khoa đã tồn tại trên hệ thống chưa
                Faculty faculty;
                if (facultyRepository.findByName(f)==null){
                    faculty=new Faculty();
                    faculty.setName(f);
                    facultyRepository.save(faculty);
                    faculties.add(faculty);
                }
                else{
                    faculty=facultyRepository.findByName(f);
                    faculties.add(faculty);
                }
            }
            List<String> add= List.of(address.split(","));


            clinic.setUrlAvatar(cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("secure_url").toString());
//            clinic.setDistrict(districtRepository.findByName(add.get(2)));
            // mẫu dữ liệu: 87 cao thắng, thanh binh, hai chau, da nang
            // set giá trị district id mỗi khi tạo
            //clinic.setDistrictId(districtRepository.findByName(add.get(add.size()-2)).getId());
            // set giá trị clinic type id mỗi khi tạo

            // đối với many to many thì chỉ cần set giá trị list cho 1 cái thôi
            // nên chỉ cần lưu list faculty vào clinic là được
            clinic.setFaculties(faculties);

            // lưu thông tin của người tạo bệnh viện
            currentUser.getInfoUser();
            clinic.setUserCreate(currentUser.getUser());
            clinic.setUsers(Arrays.asList(currentUser.getUser()));
            clinicRepository.save(clinic);

            // cái này nhằm mục đích sử dụng trong tìm kiếm
            clinic.setClinicTypeId(clinicTypeCode);
            ClinicType clinicType=clinicTypeRepository.findById(clinicTypeCode).get();
            clinicType.getClinics().add(clinic);
            clinicTypeRepository.save(clinicType);
        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }


    public void updateClinic(Long clinicId,ClinicRequest clinicRequest){
        Clinic clinic=clinicRepository.findById(clinicId).orElseThrow(()-> new RuntimeException("This clinic is non-exsit"));
        currentUser.getInfoUser();
        // không có quyền
        if (clinic.getUserCreate()!=currentUser.getUser())
            throw new RuntimeException("This action is banned");
        BeanUtils.copyProperties(clinicRequest, clinic);
        if (clinicRequest.getVietName()!=null)
            if (clinic.getVietName()!=clinicRequest.getVietName() && clinicRepository.findByAddress(clinic.getAddress())!=null)
                throw new RuntimeException("This name is exsit");
            clinic.setVietName(clinicRequest.getVietName());
        if (clinicRequest.getAddress()!=null)
            clinic.setAddress(clinicRequest.getAddress());
        if (clinicRequest.getPhone()!=null)
            clinic.setPhone(clinicRequest.getPhone());
        if (clinicRequest.getEmail()!=null)
            clinic.setEmail(clinicRequest.getEmail());
        if (clinicRequest.getUrlInfo()!=null)
            clinic.setUrlInfo(clinicRequest.getUrlInfo());
        if (clinicRequest.getFacultyNames().size()>0){
            // xóa tất cả những khoa hiện tại của bệnh viện
            clinic.getFaculties().clear();
            for (String s : clinicRequest.getFacultyNames()){
                Faculty f=facultyRepository.findByName(s);
                if (f==null){
                    Faculty newF=new Faculty();
                    newF.setName(s);
                    facultyRepository.save(newF);
                    clinic.getFaculties().add(newF);
                }
                else
                    clinic.getFaculties().add(f);
            }
        }
        clinicRepository.save(clinic);
    }

    public void updateAvatarClinic(Long clinicId, MultipartFile file){
        try{
            Clinic clinic=clinicRepository.findById(clinicId).orElseThrow(() -> new RuntimeException("This clinic is exsit"));
            String oldAvatarUrl=clinic.getUrlAvatar();
            cloudinary.uploader().destroy(FileService.getNameImage(oldAvatarUrl),ObjectUtils.emptyMap());
            clinic.setUrlAvatar(cloudinary.uploader().upload(file.getBytes(),ObjectUtils.emptyMap()).get("secure_url").toString());
            clinicRepository.save(clinic);
        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

    }

    public void deleteClinic(Long clinicId){
        try{
            Clinic clinic=clinicRepository.findById(clinicId).orElseThrow(()-> new RuntimeException("This clinic is non_exsit"));
            currentUser.getInfoUser();
            // chỉ có người tạo bệnh viện và admin mới được quyền xóa
            if (!(clinic.getUserCreate()==currentUser.getUser() || currentUser.getIsAdmin()))
                throw new RuntimeException("This action is banned");
            // đối với các quan hệ many to many thì sẽ delete trước toàn bộ code
            clinic.getFaculties().clear();
            clinicRepository.delete(clinic);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addDoctorIntoClinic(Long clinicId, String username){
        Clinic clinic=clinicRepository.findById(clinicId).orElseThrow(() -> new RuntimeException("This clinic is none-exsit"));
        User user=userRepository.findByUsername(username);
        if (user==null)
            throw new RuntimeException("This user is none-exsit");
        if (!user.getRoles().contains(roleRepository.findByCode("ROLE_DOCTOR")))
            throw new RuntimeException("This user isn't doctor");
        if (clinic.getUsers().contains(user))
            throw new RuntimeException("This user is already in clinic");
        currentUser.getInfoUser();
        if (currentUser.getUser()!=clinic.getUserCreate())
            throw new RuntimeException("This action is banned");
        clinic.getUsers().add(user);
        clinicRepository.save(clinic);
    }
}
