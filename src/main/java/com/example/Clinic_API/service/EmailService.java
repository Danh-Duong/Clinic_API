package com.example.Clinic_API.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    public JavaMailSender javaMailSender;

    public void sendEmail(String to, String code){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Thông báo xác thực tài khoản");
        message.setText("Mã xác thực của bạn là: " + code);
        javaMailSender.send(message);
    }

    public void sendEmailMime(String to, String code){
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setTo(to);
            messageHelper.setSubject("Thông báo xác thực tài khoản");
            String htmlContent = "<html><body><h1>"+code+"</h1><img src=\"https://antimatter.vn/wp-content/uploads/2022/11/hinh-nen-meo.jpg\" height=\"150px\" width=\"150px\" alt=\"\" /></body></html>";
            messageHelper.setText(htmlContent, true);
//            messageHelper.addAttachment();
        };
        javaMailSender.send(messagePreparator);
    }
}

