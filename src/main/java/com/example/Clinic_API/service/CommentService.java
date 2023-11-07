package com.example.Clinic_API.service;

//import com.example.Clinic_API.entities.Attachment;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.Clinic_API.config.CloudinaryConfig;
import com.example.Clinic_API.entities.Comment;
import com.example.Clinic_API.entities.Post;
import com.example.Clinic_API.entities.User;
import com.example.Clinic_API.payload.CommentRequest;
import com.example.Clinic_API.payload.CommentResponse;
import com.example.Clinic_API.repository.*;
import com.example.Clinic_API.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CommentRepository commentRepository;

//    @Autowired
//    AttachmentTypeRepository attachmentTypeRepository;

    @Autowired
    private final CurrentUser currentUser=new CurrentUser();

    @Autowired
    PostRepository postRepository;

    @Autowired
    Cloudinary cloudinary;


    public List<CommentResponse> getCommentDoctor(Integer limit,Long doctorId){
        User doctor=userRepository.findById(doctorId).orElseThrow(() -> new RuntimeException("This doctor is non-exit"));
        if (!doctor.getRoles().contains(roleRepository.findByCode("ROLE_DOCTOR")))
            throw new RuntimeException("This user is not a doctor");
        List<CommentResponse> comments=new ArrayList<>();
        Pageable pageable= PageRequest.of(0,limit, Sort.by("createAt").descending());
        for (Comment c: commentRepository.findByDoctorId(doctorId,pageable)){
            String avatar=c.getUser().getAvatarUrl();
//            for (Attachment attachment: c.getUser().getAttachments())
//                if (attachment.getAttachmentType()==attachmentTypeRepository.findByCode("AVATAR"))
//                    avatar=attachment.getFileUrl();
            if (avatar=="")
                avatar="https://facebookninja.vn/wp-content/uploads/2023/06/anh-dai-dien-mac-dinh-zalo.jpg";
            comments.add(new CommentResponse(avatar, c.getUser().getUsername(),c.getContent(),c.getCreateAt()));
        }
        return comments;
    }

    public void createDoctorComment(Long doctorId, CommentRequest commentRequest){
        // kiểm tra bác sĩ
        User doctor=userRepository.findById(doctorId).orElseThrow(() -> new RuntimeException("This doctor is non-exit"));
        if (!doctor.getRoles().contains(roleRepository.findByCode("ROLE_DOCTOR")))
            throw new RuntimeException("This user is not a doctor");
        currentUser.getInfoUser();
        Comment comment=new Comment();
        comment.setDoctor(doctor);
        comment.setContent(commentRequest.getContent());
        comment.setUser(currentUser.getUser());
        commentRepository.save(comment);
    }


    public void updateDoctorComment(Long commentId, CommentRequest commentRequest){
        Comment comment=commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("This comment is non-exsit"));
        currentUser.getInfoUser();
        if (!(comment.getUser()==currentUser.getUser() || currentUser.getIsAdmin()))
            throw new RuntimeException("This action is banner");
        comment.setContent(commentRequest.getContent());
        commentRepository.save(comment);
    }

    public void deleteDoctorComment(Long commentId){
        Comment comment=commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("This comment doctor is non-exsit"));
        currentUser.getInfoUser();
        if (!(comment.getUser()==currentUser.getUser() || currentUser.getIsAdmin()))
            throw new RuntimeException("This action is banner");
        commentRepository.delete(comment);
    }

    // comment của bài blog
    public List<CommentResponse> getCommentPost(Long postId, Integer limit){
        Post post=postRepository.findById(postId).orElseThrow(() -> new RuntimeException("This blog is non-exsit"));
        Pageable pageable=PageRequest.of(0,limit,Sort.by("createAt").descending());
        List<CommentResponse> comments=new ArrayList<>();
        // danh sách các hình ảnh bình luận của bài viết
        for (Comment c: commentRepository.findByPostId(postId,pageable))
            comments.add(new CommentResponse(c.getUser().getAvatarUrl(),c.getUser().getUsername(),c.getContent(),c.getCreateAt(),c.getImgUrl()));
        return comments;
    }

    // tạo commet cho bài blog
    public void createPostComment(Long postId,MultipartFile file,String content){
        try{
            Post post=postRepository.findById(postId).orElseThrow(()-> new RuntimeException("This post is non-exsit"));
            currentUser.getInfoUser();
            Comment comment=new Comment();
            comment.setContent(content);
            comment.setUser(currentUser.getUser());
            comment.setPost(post);
            if (!(FileService.isNotEmpty(file) && FileService.isImageFile(file) && FileService.isValidSize(file,5))){
                // đưa ra các Exception
            }
            comment.setImgUrl(cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("secure_url").toString());
            commentRepository.save(comment);
        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

    }

    public void updatePostComment(Long commentId, MultipartFile file, String content){
        try{
            Comment comment=commentRepository.findById(commentId).orElseThrow(()-> new RuntimeException("This comment Post is none-exsit"));
            currentUser.getInfoUser();
            if (!(currentUser.getUser()==comment.getUser()))
                throw new RuntimeException("This action is banned");
            if (content!=null)
                comment.setContent(content);
            if (file!=null) {
                if (!(FileService.isNotEmpty(file) && FileService.isImageFile(file) && FileService.isValidSize(file, 5))) {
                    // đưa ra các Exception
                }
                String oldImgUrl = comment.getImgUrl();
                cloudinary.uploader().destroy(FileService.getNameImage(oldImgUrl), ObjectUtils.emptyMap());
                comment.setImgUrl(cloudinary.uploader().upload(file.getBytes(),ObjectUtils.emptyMap()).get("secure_url").toString());
            }
            commentRepository.save(comment);
        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deletePostComment(Long commentId){
        Comment comment=commentRepository.findById(commentId).orElseThrow(()-> new RuntimeException("This comment Post is none-exsit"));
        currentUser.getInfoUser();
        if (!(currentUser.getIsAdmin() || currentUser.getUser()==comment.getUser()))
            throw new RuntimeException("This action is banned");
        commentRepository.delete(comment);
    }
}
