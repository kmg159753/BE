package com.example.newnique.subscription.handler;

import com.example.newnique.subscription.dto.MailSendDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSender {

    private final JavaMailSender javaMailSender;
    private static final String FROM_ADDRESS = "dominica573@naver.com";

    @Async
    public void sendMail(MailSendDto maildto) throws  MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message,"UTF-8");
        helper.setFrom(FROM_ADDRESS);
        helper.setTo(maildto.getToAddress());
        helper.setSubject(maildto.getTitle());
        helper.setText(maildto.getMessage());
        javaMailSender.send(message);



    }
}
