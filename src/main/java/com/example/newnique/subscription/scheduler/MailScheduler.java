package com.example.newnique.subscription.scheduler;

import com.example.newnique.news.entity.News;
import com.example.newnique.news.repository.NewsRepository;
import com.example.newnique.subscription.dto.MailSendDto;
import com.example.newnique.subscription.entity.Subscription;
import com.example.newnique.subscription.handler.EmailSender;
import com.example.newnique.subscription.repository.SubscriptionRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



@Component
@RequiredArgsConstructor
public class MailScheduler {
    private final SubscriptionRepository subscriptionRepository;
    private final NewsRepository newsRepository;
    private final EmailSender emailSender;

//    @PostConstruct
//    public void init() throws  MessagingException {
//        sendEmail(); // 프로그램 시작 시에 한 번 실행(테스트 용도)
//    }


    @Transactional
    @Scheduled(cron="0 3 3 * * *")
    public void sendEmail() throws MessagingException {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        News news = newsRepository.findTopByOrderByCreatedAtDesc();
        for (Subscription subscription: subscriptions) {
            String email = subscription.getEmail();
            String nickname = subscription.getNickname();
            String content;

            if(nickname == null){
                String[] nameByEmail = email.split("@");
                content = " 안녕하세요! " +nameByEmail[0] +"님 ! 아래의 뉴스를 전해 드리러 왔어요 \n\n\n"
                        +news.getTitle() +"\n" + news.getContent();
            }else{
                content =  " 안녕하세요! " +nickname +"님 ! 아래의 뉴스를 전해 드리러 왔어요 \n\n\n"+news.getTitle()+"\n\n"+news.getContent();
            }

            emailSender.sendMail(
                    MailSendDto.builder()
                            .toAddress(email)
                            .title(news.getTitle())
                            .message(content)
                            .build()
            );
        }

    }

}
