package com.example.newnique.newsletter.service;

import com.example.newnique.newsletter.dto.SubscriptionRequestDto;
import com.example.newnique.newsletter.entity.Subscription;
import com.example.newnique.newsletter.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    public Subscription createSub(SubscriptionRequestDto subscriptionRequestDto) {
        Subscription subscription = new Subscription(subscriptionRequestDto);
        Subscription saveSubscription = subscriptionRepository.save(subscription);

        return saveSubscription;



    }
}
