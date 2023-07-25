package com.example.newnique.newsletter.dto;


import com.example.newnique.newsletter.entity.Subscription;
import lombok.Getter;

@Getter
public class SubscriptionResponseDto {
    private String nickName;
    private String email;

    public SubscriptionResponseDto(Subscription subscription) {
        this.nickName = subscription.getNickname();
        this.email = subscription.getEmail();
    }
}
