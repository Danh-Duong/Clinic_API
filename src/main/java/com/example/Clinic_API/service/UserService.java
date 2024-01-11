package com.example.Clinic_API.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
//import com.example.Clinic_API.entities.Attachment;
import com.example.Clinic_API.entities.User;
import com.example.Clinic_API.payload.ChangePassRequest;
import com.example.Clinic_API.payload.UserUpdateRequest;
//import com.example.Clinic_API.repository.AttachmentRepository;
//import com.example.Clinic_API.repository.AttachmentTypeRepository;
import com.example.Clinic_API.repository.UserRepository;
import com.example.Clinic_API.security.CurrentUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Optional;

import static com.example.Clinic_API.service.FileService.*;

@Service
public class UserService {

    @Autowired
    private final CurrentUser currentUser = new CurrentUser();

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;

//    @Autowired
//    AttachmentService attachmentService;

    @Autowired
    Cloudinary cloudinary;

//    @Autowired
//    AttachmentTypeRepository attachmentTypeRepository;
//
//    @Autowired
//    AttachmentRepository attachmentRepository;

    public void changePass(ChangePassRequest request){
        currentUser.getInfoUser();
        User user=currentUser.getUser();
        if (user==null)
            throw new RuntimeException("Got unexcepted error");
        // mã hóa mât khẩu cũ để kiểm tra
        // tham số đầu là pass của request ở dạng ban đầu
        // tham số 2 là pass hiện tại của user ở dạng đã mã hóa
        if (!passwordEncoder.matches(request.getOldPass(), user.getPassword())){
            throw new RuntimeException("Password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPass()));
        userRepository.save(user);
    }

    public void updateAvatar(Long id,MultipartFile multipartFile){
        try{
            currentUser.getInfoUser();
            if (userRepository.findById(id).get()!=currentUser.getUser())
                throw new RuntimeException("This action is invalid");
            if (!(isImageFile(multipartFile) && isValidSize(multipartFile,5))){

            }
            User user=currentUser.getUser();
            if (user==null)
                throw new RuntimeException("This action gets unexpected error");

            // kiểm tra file ảnh đã tồn tại chưa
//            Attachment ava=attachmentRepository.findByUserAndAttachmentType(user, attachmentTypeRepository.findByCode("AVATAR"));
            String ava=user.getAvatarUrl();
            String image = cloudinary.uploader().upload(multipartFile.getBytes(), ObjectUtils.emptyMap()).get("secure_url").toString();
            if (ava==null){
                // chưa có ảnh
//                Attachment attachment=new Attachment();
//                attachment.setUser(user);
//                attachment.setFileName("avatar/" + user.getId());
//                attachment.setFileUrl(image);
//                attachment.setAttachmentType(attachmentTypeRepository.findByCode("AVATAR"));
//                attachmentRepository.save(attachment);
                ava=image;
            }
            else{
                if (getNameImage(ava)!=null)
                    // xóa ảnh được lưu trên cloudinary
                        cloudinary.uploader().destroy(getNameImage(ava), ObjectUtils.emptyMap());
//                ava.setFileName("avatar/" + user.getId());
//                ava.setFileUrl(image);
//                attachmentRepository.save(ava);
            }
            userRepository.save(user);
        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

    }


    public void updateInfoUser(Long id,UserUpdateRequest userUpdateRequest){
        try{
            User user=currentUser.getInfoUser().getUser();
            if (userRepository.findById(id).get()!=user)
                throw new RuntimeException("This action is banned");
            if (user==null)
                throw new RuntimeException("This user is none-exsit");
            BeanUtils.copyProperties(userUpdateRequest, user);
            SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
            user.setBirthDate(format.parse(userUpdateRequest.getBirthDate()));
            userRepository.save(user);
        }
        catch (Exception e){}
    }

    public User getInfoUserById(Long userId){
        Optional<User>user= userRepository.findById(userId);
        if (!user.isPresent())
            throw new RuntimeException("This user is none-exsit");
        return user.get();
    }

    public void deleteUserById(Long userId){
        Optional<User>user= userRepository.findById(userId);
        if (!user.isPresent())
            throw new RuntimeException("This user is none-exsit");
        userRepository.delete(user.get());
    }

}
