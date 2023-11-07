package com.example.Clinic_API.service;

import com.example.Clinic_API.entities.EmailToken;
import com.example.Clinic_API.entities.User;
import com.example.Clinic_API.payload.ForgetPassRequest;
import com.example.Clinic_API.payload.ResetPassRequest;
import com.example.Clinic_API.repository.EmailTokenRepository;
import com.example.Clinic_API.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class AuthenService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    EmailTokenRepository emailTokenRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    private final Long timeExpired= 1000*60*1L;

    // thời gian ở đây là 60s
    @Scheduled(fixedRate = 60*1000)
    public void cleanTokenAuto(){
        List<EmailToken> emailTokens=emailTokenRepository.findAll();
        for (EmailToken email: emailTokens)
            if (!checkTokenExpired(email))
                emailTokenRepository.delete(email);
    }

    public void forgotPass(ForgetPassRequest request){
        User user=userRepository.findByUsername(request.getUsername());
        if (user==null)
            throw new RuntimeException("This user is none-exsit");
        if (emailTokenRepository.findByUser(user)!=null)
            emailTokenRepository.delete(emailTokenRepository.findByUser(user));
        EmailToken emailToken=new EmailToken();
        emailToken.setToken(String.valueOf(generateCode(5)));
        emailToken.setDateExpired(new Date(new Date().getTime()+ timeExpired));
        emailToken.setUser(user);

        emailTokenRepository.save(emailToken);

        emailService.sendEmailMime(user.getEmail(),emailToken.getToken());
    }


    public void resetPass(ResetPassRequest request){
        EmailToken emailToken= emailTokenRepository.findByToken(request.getToken());
        User userRequest= userRepository.findByUsername(request.getUsername());
        // không trùng user
        if (userRequest.getId()!=emailToken.getUser().getId()){
            throw new RuntimeException("This action is invalid");
        }
        if (emailToken==null)
            throw new RuntimeException("This token is none-exsit");
        // token đã hết hạn
//        if (!checkTokenExpired(emailToken)){
//            emailTokenRepository.delete(emailToken);
//            throw new RuntimeException("This token is expired");
//        }

        userRequest.setPassword(passwordEncoder.encode(request.getNewPass()));
        userRepository.save(userRequest);
        emailTokenRepository.delete(emailToken);
    }

    // mục đích sử dụng StringBuilder là để tiết kiếm bộ nhớ
    // về nếu sử dụng String thì tạo nên 1 vùng nhớ mới
    // gây năng chương trình.
    public String generateCode(int len){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuilder stringBuilder=new StringBuilder();
        for (int i=1;i<=len;i++){
            int so=random.nextInt(str.length());
            stringBuilder.append(str.charAt(so));
        }

        return stringBuilder.toString();

    }

    public Boolean checkTokenExpired(EmailToken emailToken){
        Date now=new Date();
        if (emailToken.getDateExpired().before(now))
            return true;
        return false;
    }
}
