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

    public static final String RESET_CODE="@reset_code";
    public static final String TIME="00:00:00";
    public static final String DESTINATION="abc12344";

    private static final String RESET_PASS_MESS=
            "Thông báo !!!\n"
            +"Mã code để reset mật khẩu ứng dụng là: " + RESET_CODE +"\n"
            +"Vui lòng không chia sẻ mã code này.";

    private static final String CONFIRM_BOOKING_SUCC_MESS=
            "Thông báo !!!\n"
            +"Lịch khám bệnh được đăng ký thành công.\n"
            +"Thời gian:" + TIME+ "\n"
            +"Địa điểm:"  + DESTINATION + "\n"
            +"Cảm ơn bạn đã tin tưởng chúng tôi";

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

    public void sendResetPassEmail(String to,String code){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Thông báo");
        message.setText(RESET_PASS_MESS.replace(RESET_CODE,code));
        javaMailSender.send(message);
    }

    public void sendconfirmBookingSucess(String to, String time, String destination){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Thông báo đặt lịch thành công");
        message.setSubject(CONFIRM_BOOKING_SUCC_MESS.replace(TIME, time).replace(DESTINATION, destination));
        javaMailSender.send(message);

    }
}

