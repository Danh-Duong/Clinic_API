package com.example.Clinic_API.service;

//import com.example.Clinic_API.entities.Attachment;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.Clinic_API.config.CloudinaryConfig;
import com.example.Clinic_API.entities.*;
import com.example.Clinic_API.payload.CommentRequest;
import com.example.Clinic_API.payload.CommentResponse;
import com.example.Clinic_API.repository.*;
import com.example.Clinic_API.security.CurrentUser;
import org.modelmapper.ModelMapper;
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

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private AttachmentRepository attachmentRepository;


    // sắp xếp theo thời gian
    public List<CommentResponse> getComment(Integer limit,Long doctorId, Long postId){
        User doctor=userRepository.findById(doctorId).orElseThrow(() -> new RuntimeException("This doctor doesn't exsit"));
        if (!doctor.getRoles().contains(roleRepository.findByCode("ROLE_DOCTOR")))
            throw new RuntimeException("This user is not a doctor");
        Post post=postRepository.findById(postId).orElseThrow(() -> new RuntimeException("This post doesn't exsit"));
        List<CommentResponse> responses=new ArrayList<>();
        Pageable pageable= PageRequest.of(0,limit, Sort.by("createAt").descending());
        List<Comment> comments = null;
        // lấy danh sách các bình luận theo bài post
        if (postId!=null) {
            comments = commentRepository.findByPostId(postId, pageable);
        }
        else if (doctorId!=null)
            comments=commentRepository.findByDoctorId(doctorId,pageable);
        for (Comment c: comments){
            String avatar=c.getUser().getAvatarUrl();
            responses.add(new CommentResponse(avatar, c.getUser().getUsername(),c.getContent(),c.getUpdateAt(),c.getAttachment().getUrl()));
        }
        return responses;
    }
//
    public void createDoctorComment(Long doctorId, Long postId, CommentRequest commentRequest){
        // kiểm tra bác sĩ
        try {
            User doctor = userRepository.findById(doctorId).orElseThrow(() -> new RuntimeException("This doctor doesn't exsit"));
            if (!doctor.getRoles().contains(roleRepository.findByCode("ROLE_DOCTOR")))
                throw new RuntimeException("This user is not a doctor");
            Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("This post doesn't exsit"));
            currentUser.getInfoUser();
            Comment comment = new Comment();
            // tạo bình luận cho bác sĩ
            if (doctorId != null)
                comment.setDoctor(doctor);
            if (postId != null) {
                comment.setPost(post);
                if (commentRequest.getImage() != null) {
                    String url = cloudinary.uploader().upload(commentRequest.getImage().getBytes(), ObjectUtils.emptyMap()).get("secure_url").toString();
                    Attachment attachment= new Attachment();
                    attachment.setPost(post);
                    attachment.setUrl(url);
                    comment.setAttachment(attachment);
                }
            }

            comment.setContent(commentRequest.getContent());
            comment.setUser(currentUser.getUser());
            commentRepository.save(comment);
        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
//
//
    public void updateComment(Long commentId, CommentRequest commentRequest){
        try{
        Comment comment=commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("This comment is doesn't exsit"));
        currentUser.getInfoUser();
        if (!(comment.getUser()==currentUser.getUser() || currentUser.getIsAdmin()))
            throw new RuntimeException("This action is banner");
        comment.setContent(commentRequest.getContent());
        if (commentRequest.getImage()!=null)
        {
            attachmentRepository.deleteAttachmentByComment(comment);
            String url= cloudinary.uploader().upload(commentRequest.getImage().getBytes(), ObjectUtils.emptyMap()).get("secure_").toString();
            Attachment attachment= new Attachment();
            attachment.setPost(comment.getPost());
            attachment.setUrl(url);
            comment.setAttachment(attachment);
        }
        commentRepository.save(comment);}
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
//
    public void deleteComment(Long commentId){
        Comment comment=commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("This comment is doesn't exsit"));
        currentUser.getInfoUser();
        if (!(comment.getUser()==currentUser.getUser() || currentUser.getIsAdmin()))
            throw new RuntimeException("This action is banner");
        commentRepository.delete(comment);
    }

    // comment của bài blog
//    public List<CommentResponse> getCommentPost(Long postId, Integer limit){
//        Post post=postRepository.findById(postId).orElseThrow(() -> new RuntimeException("This blog is non-exsit"));
//        Pageable pageable=PageRequest.of(0,limit,Sort.by("createAt").descending());
//        List<CommentResponse> comments=new ArrayList<>();
//        // danh sách các hình ảnh bình luận của bài viết
//        for (Comment c: commentRepository.findByPostId(postId,pageable))
//            comments.add(new CommentResponse(c.getUser().getAvatarUrl(),c.getUser().getUsername(),c.getContent(),c.getCreateAt(),c.getImgUrl()));
//        return comments;
//    }
//
//    // tạo commet cho bài blog
//    public void createPostComment(Long postId,MultipartFile file,String content){
//        try{
//            Post post=postRepository.findById(postId).orElseThrow(()-> new RuntimeException("This post is non-exsit"));
//            currentUser.getInfoUser();
//            Comment comment=new Comment();
//            comment.setContent(content);
//            comment.setUser(currentUser.getUser());
//            comment.setPost(post);
//            if (!(FileService.isNotEmpty(file) && FileService.isImageFile(file) && FileService.isValidSize(file,5))){
//                // đưa ra các Exception
//            }
//            comment.setImgUrl(cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("secure_url").toString());
//            commentRepository.save(comment);
//        }
//        catch (Exception e){
//            throw new RuntimeException(e.getMessage());
//        }
//
//    }
//
//    public void updatePostComment(Long commentId, MultipartFile file, String content){
//        try{
//            Comment comment=commentRepository.findById(commentId).orElseThrow(()-> new RuntimeException("This comment Post is none-exsit"));
//            currentUser.getInfoUser();
//            if (!(currentUser.getUser()==comment.getUser()))
//                throw new RuntimeException("This action is banned");
//            if (content!=null)
//                comment.setContent(content);
//            if (file!=null) {
//                if (!(FileService.isNotEmpty(file) && FileService.isImageFile(file) && FileService.isValidSize(file, 5))) {
//                    // đưa ra các Exception
//                }
//                String oldImgUrl = comment.getImgUrl();
//                cloudinary.uploader().destroy(FileService.getNameImage(oldImgUrl), ObjectUtils.emptyMap());
//                comment.setImgUrl(cloudinary.uploader().upload(file.getBytes(),ObjectUtils.emptyMap()).get("secure_url").toString());
//            }
//            commentRepository.save(comment);
//        }
//        catch (Exception e){
//            throw new RuntimeException(e.getMessage());
//        }
//    }
//
//    public void deletePostComment(Long commentId){
//        Comment comment=commentRepository.findById(commentId).orElseThrow(()-> new RuntimeException("This comment Post is none-exsit"));
//        currentUser.getInfoUser();
//        if (!(currentUser.getIsAdmin() || currentUser.getUser()==comment.getUser()))
//            throw new RuntimeException("This action is banned");
//        commentRepository.delete(comment);
//    }
}
