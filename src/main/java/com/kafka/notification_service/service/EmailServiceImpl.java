package com.kafka.notification_service.service;

import com.kafka.notification_service.entity.Notification;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(Notification notification){
        try {
            MimeMessage mimeMessage=mailSender.createMimeMessage();
            MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true);
            helper.setTo(notification.getRecipientEmail());
            helper.setSubject(notification.getSubject());
            helper.setText(notification.getBody(),true);
            mailSender.send(mimeMessage);
            log.info("""
                    ======================================
                    Email Sent Successfully
                    Recipient : {}
                    Subject   : {}
                    ======================================
                    """,
                    notification.getRecipientEmail(),
                    notification.getSubject());
        }
        catch (MessagingException ex){
            throw new RuntimeException(ex);
        }
    }
}