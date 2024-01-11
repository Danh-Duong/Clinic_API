package com.example.Clinic_API.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.Clinic_API.entities.Attachment;
import com.example.Clinic_API.entities.Post;
import com.example.Clinic_API.entities.StatusPost;
import com.example.Clinic_API.entities.User;
import com.example.Clinic_API.payload.StatusEnum;
import com.example.Clinic_API.repository.PostRepository;
import com.example.Clinic_API.repository.PostTypeRepository;
import com.example.Clinic_API.repository.StatusRepository;
import com.example.Clinic_API.repository.UserRepository;
import com.example.Clinic_API.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    final CurrentUser currentUser=new CurrentUser();

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostTypeRepository postTypeRepository;

    @Autowired
    Cloudinary cloudinary;

    @Autowired
    StatusRepository statusRepository;

    public List<Post> getPosts(Long userId, Integer limit){
        User user=userRepository.findById(userId).orElseThrow(()-> new RuntimeException("This user is none-exsit"));
        return user.getPosts();
    }

//    public void createPost(String title,String content, Long postTypeId, MultipartFile[] files){
//        // lấy thông tin tất cả hiện tại của user
//        try{
//            currentUser.getInfoUser();
//            Post post=new Post();
//            post.setTitle(title);
//            post.setContent(content);
//            post.setPostType(postTypeRepository.findById(postTypeId).get());
//            post.setUser(currentUser.getUser());
//            List<Attachment> attachments=new ArrayList<>();
//            if (files!=null && files.length>0){
//                System.out.println("oke");
//                for (MultipartFile file: files){
//                    String urlImage=cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("secure_url").toString();
//                    attachments.add(new Attachment("",urlImage,post));
//                }
//                post.setAttachments(attachments);
//            }
//            postRepository.save(post);
//        }
//        catch (Exception e){
//            throw new RuntimeException(e.getMessage());
//        }
//
//    }

//    public void updatePost(Long postId,String title,String content, Long postTypeId, MultipartFile[] files){
//        try{
//            Post post=postRepository.findById(postId).orElseThrow(() -> new RuntimeException("This post is none-exit"));
//            // lấy thông tin user hiện tại
//            currentUser.getInfoUser();
//            if (post.getUser()==currentUser.getUser()){
//                if (title!=null)
//                    post.setTitle(title);
//                if (content!=null)
//                    post.setContent(content);
//                if (postTypeId!=null)
//                    post.setPostType(postTypeRepository.findById(postTypeId).get());
//                if (files!=null && files.length>0){
//                    post.getAttachments().clear();
//                    postRepository.save(post);
//                    for (MultipartFile file: files){
//                        String urlImage=cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("secure_url").toString();
//                        post.getAttachments().add(new Attachment("",urlImage,post));
//                    }
//                }
//                postRepository.save(post);
//            }
//            else
//                throw new RuntimeException("This action is banned");
//        }
//        catch (Exception e){
//            throw new RuntimeException(e.getMessage());
//        }
//    }
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
