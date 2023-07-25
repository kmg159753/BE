package com.example.newnique.newsletter.dto;

import com.example.newnique.newsletter.entity.Subscription;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SubscriptionRequestDto {
    private String nickName;
    private String email;

    public SubscriptionRequestDto(Subscription subscription) {
        this.nickName = subscription.getNickname();
        this.email = subscription.getNickname();
    }
}
