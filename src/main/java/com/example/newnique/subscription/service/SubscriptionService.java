package com.example.newnique.subscription.service;

import com.example.newnique.news.entity.News;
import com.example.newnique.news.repository.NewsRepository;
import com.example.newnique.subscription.dto.MailSendDto;
import com.example.newnique.subscription.dto.SubscriptionRequestDto;
import com.example.newnique.subscription.dto.SubscriptionResponseDto;
import com.example.newnique.subscription.entity.Subscription;
import com.example.newnique.subscription.handler.EmailSender;
import com.example.newnique.subscription.repository.SubscriptionRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final NewsRepository newsRepository;
    private final EmailSender emailSender;

    public SubscriptionResponseDto createSub(SubscriptionRequestDto subscriptionRequestDto) throws MessagingException {

        Subscription saveSubscription = subscriptionRepository.save(Subscription.builder()
                .email(subscriptionRequestDto.getEmail())
                .nickname(subscriptionRequestDto.getNickName())
                .build()
        );

        News news = newsRepository.findTopByOrderByCreatedAtDesc();

        emailSender.sendMail(
                MailSendDto.builder()
                        .title(subscriptionRequestDto.getNickName() + "님 환영합니다")
                        .message("뉴닉을 구독해주신걸 환영합니다 !\n\n\n" + news.getTitle() + "\n\n\n" + news.getNewsSummary())
                        .toAddress(subscriptionRequestDto.getEmail())
                        .build()
        );

        return SubscriptionResponseDto.builder()
                .email(saveSubscription.getEmail())
                .nickName(saveSubscription.getNickname())
                .build();
    }
}
