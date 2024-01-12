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
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    // 2 phút
    private final Long timeExpired= (long) 1000 * 60 * 2;

    // thời gian ở đây là 60s
//    @Scheduled(fixedRate = 60*1000)
//    public void cleanTokenAuto(){
//        List<EmailToken> emailTokens=emailTokenRepository.findAll();
//        for (EmailToken email: emailTokens)
//            if (!checkTokenExpired(email))
//                emailTokenRepository.delete(email);
//    }

    public void forgotPass(ForgetPassRequest request){
        User user=userRepository.findByUsername(request.getUsernameOrEmail());
        if (user==null)
            user=userRepository.findByEmail(request.getUsernameOrEmail());
        if (user==null)
            throw new RuntimeException("This user is none-exsit");
        EmailToken emailToken=emailTokenRepository.findByUser(user);
        if (emailToken==null){
            emailToken = new EmailToken();
            emailToken.setUser(user);
        }

        emailToken.setToken(String.valueOf(generateCode(5)));
//        Instant expirationInstant = Instant.now().plus(Duration.ofMillis(timeExpired));
//        emailToken.setDateExpired(Date.from(expirationInstant));
        emailToken.setDateExpired(new Date(new Date().getTime() + timeExpired));
        emailTokenRepository.save(emailToken);

        ExecutorService executor= Executors.newFixedThreadPool(1);
        // dùng final vì executor cần đảm bảo 1 biến ổn định
        User finalUser = user;
        EmailToken finalEmailToken = emailToken;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                 emailService.sendResetPassEmail(finalUser.getEmail(), finalEmailToken.getToken());
            }
        });

    }


    public void resetPass(ResetPassRequest request){
        EmailToken emailToken= emailTokenRepository.findByToken(request.getToken());
        User userRequest= userRepository.findByUsername(request.getEmailOrUsername());
        if (userRequest==null)
            userRequest=userRepository.findByEmail(request.getEmailOrUsername());
        if (userRequest==null)
            throw new RuntimeException("This user doesn't exist");
        // không trùng user
        if (userRequest.getId()!=emailToken.getUser().getId()){
            throw new RuntimeException("This action is invalid");
        }
        if (emailToken==null)
            throw new RuntimeException("This token doesn't exist");
        // token đã hết hạn
        if (!checkTokenExpired(emailToken)){
            emailTokenRepository.delete(emailToken);
            throw new RuntimeException("This token is already expired");
        }

        userRequest.setPassword(passwordEncoder.encode(request.getNewPass()));
        userRepository.save(userRequest);
        emailTokenRepository.delete(emailToken);
    }

    // mục đích sử dụng StringBuilder là để tiết kiếm bộ nhớ
    // về nếu sử dụng String thì tạo nên 1 vùng nhớ mới
    // gây nặng chương trình.
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
        if (emailToken.getDateExpired().after(now))
            return true;
        return false;
    }
}
