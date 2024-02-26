package com.example.Clinic_API.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.Clinic_API.entities.*;
import com.example.Clinic_API.payload.PostRequest;
import com.example.Clinic_API.payload.StatusEnum;
import com.example.Clinic_API.repository.*;
import com.example.Clinic_API.security.CurrentUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AttachmentTypeRepository attachmentTypeRepository;

    @Autowired
    final CurrentUser currentUser=new CurrentUser();

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostTypeRepository postTypeRepository;

    @Autowired
    Cloudinary cloudinary;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    StatusRepository statusRepository;
    @Autowired
    private ClinicRepository clinicRepository;

    private static Logger logger=LoggerFactory.getLogger(PostService.class);

    public List<Post> getPosts(Long userId, Long clinicId, Integer limit, Integer page){
        Pageable pg=PageRequest.of(page-1, limit);
        // lấy bài post theo user;
        if (userId!=null){
            User user=userRepository.findById(userId).orElseThrow(()-> new RuntimeException("This user is none-exsit"));
//            return user.getPosts();
            return postRepository.findByUserPost(user.getId(),pg);
        }
        // lấy bài post theo clinic
        return postRepository.findByClinicId(clinicId,pg);
    }

    public void createPost(PostRequest postRequest,String type){
        // lấy thông tin tất cả hiện tại của user
        try{
            currentUser.getInfoUser();
            Post post=new Post();
            post.setTitle(postRequest.getTitle());
            post.setContent(postRequest.getContent());
//            post.setPostType(postTypeRepository.findById(postRequest.getPostTypeId()).get());
            post.setUser(currentUser.getUser());
            // Khi tạo blog cho clinic
            Clinic clinic=null;
            if (type.equals("clinic"))
            {
                clinic=clinicRepository.findById(postRequest.getClinicId()).orElseThrow(() -> new RuntimeException("This clinic doesn't exsit"));
                if (clinic.getUserCreate()==currentUser.getUser())
                    post.setClinic(clinic);
            }

            List<Attachment> attachments=new ArrayList<>();
            if (postRequest.getFiles()!=null && postRequest.getFiles().length>0){
                AttachmentType attachmentType=attachmentTypeRepository.findByCode("POST");
                for (MultipartFile file: postRequest.getFiles()){
                    String urlImage=cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("secure_url").toString();
                    attachments.add(new Attachment(urlImage,post,currentUser.getUser(),clinic,attachmentType));
                }
                post.setAttachments(attachments);
            }
            postRepository.save(post);
        }
        catch (Exception e){
//            throw new RuntimeException(e.getMessage());
            e.printStackTrace();
        }

    }

    public void updatePost(Long postId, PostRequest postRequest){
        try{
            Post post=postRepository.findById(postId).orElseThrow(() -> new RuntimeException("This post doesn't exsit"));
            // lấy thông tin user hiện tại
            Clinic clinic=post.getClinic();
            currentUser.getInfoUser();
            if (post.getUser()==currentUser.getUser()){
                if (postRequest.getTitle()!=null)
                    post.setTitle(postRequest.getTitle());
                if (postRequest.getContent()!=null)
                    post.setContent(postRequest.getContent());
//                if (postTypeId!=null)
//                    post.setPostType(postTypeRepository.findById(postTypeId).get());

                if (postRequest.getFiles()!=null && postRequest.getFiles().length>0){
                    // xóa sạch file cũ
                    // chú ý xóa dữ liệu trên cloudinary
                    attachmentRepository.deleteAttachmentByPost(post);
                    postRepository.save(post);
                    AttachmentType attachmentType= attachmentTypeRepository.findByCode("POST");
                    for (MultipartFile file: postRequest.getFiles()){
                        String urlImage=cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("secure_url").toString();
                        post.getAttachments().add(new Attachment(urlImage,post,currentUser.getUser(),clinic, attachmentType));
                    }
                }
                postRepository.save(post);
            }
            else
                throw new RuntimeException("This action is banned");
        }
        catch (Exception e){
//            throw new RuntimeException(e.getMessage());
            e.printStackTrace();
        }

    }
//
//    public void deletePost(Long postId){
//        Post post=postRepository.findById(postId).orElseThrow(() -> new RuntimeException("This post is none-exsit"));
//        currentUser.getInfoUser();
//        if (!(currentUser.getIsAdmin() || currentUser.getUser()==post.getUser()))
//            throw new RuntimeException("This action is banned");
//        postRepository.delete(post);
//    }
//
//    public void updateStatus(Long postId, String statusName){
//        try {
//            Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("This post is none-exsit"));
//            currentUser.getInfoUser();
//            StatusPost statusPost = statusRepository.findByUserAndPost(currentUser.getUser(), post);
//            // này là tạo mới
//            if (statusPost == null) {
//                StatusPost newStatusPost = new StatusPost();
//                newStatusPost.setUser(currentUser.getUser());
//                newStatusPost.setPost(post);
//                newStatusPost.setStatus(statusName);
//                if (statusName.equals(StatusEnum.LIKE.name()))
//                    post.setLikeStatus(post.getLikeStatus() + 1);
//                else if (statusName.equals(StatusEnum.DISLIKE.name()))
//                    post.setDislikeStatus(post.getDislikeStatus() + 1);
//                statusRepository.save(newStatusPost);
//            }
//            // cập nhập status của bài post
//            else {
//                // gán giá trị trung gian
//                String s = statusPost.getStatus();
//                // set giá trị thực
//                statusPost.setStatus(statusName);
//                statusRepository.save(statusPost);
//                if (statusName.equals(StatusEnum.NONE.name()))
//                    statusRepository.delete(statusPost);
//                if (s.equals(StatusEnum.LIKE.name()) && !statusName.equals(StatusEnum.LIKE.name())){
//                        post.setLikeStatus(post.getLikeStatus() - 1);
//                        if (statusName.equals(StatusEnum.DISLIKE.name()))
//                            post.setDislikeStatus(post.getDislikeStatus() + 1);
//                } else if (s.equals(StatusEnum.DISLIKE.name()) && !statusName.equals(StatusEnum.DISLIKE.name())) {
//                    post.setDislikeStatus(post.getDislikeStatus() - 1);
//                    if (statusName.equals(StatusEnum.LIKE.name()))
//                        post.setLikeStatus(post.getLikeStatus() + 1);
//                }
//
//            }
//            postRepository.save(post);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
}
