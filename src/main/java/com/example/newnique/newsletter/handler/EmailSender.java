package com.example.newnique.newsletter.handler;

import com.example.newnique.newsletter.dto.Maildto;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class EmailSender {

    private final JavaMailSender javaMailSender;
    private static final String FROM_ADDRESS = "dominica573@naver.com";

    @Async
    public void sendMail(Maildto maildto) throws  MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message,"UTF-8");
        helper.setFrom(FROM_ADDRESS);
        helper.setTo(maildto.getToAddress());
        helper.setSubject(maildto.getTitle());
        helper.setText(maildto.getMessage());
        javaMailSender.send(message);



    }
}
