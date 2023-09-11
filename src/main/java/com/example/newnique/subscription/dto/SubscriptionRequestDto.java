package com.example.newnique.subscription.dto;

import com.example.newnique.subscription.entity.Subscription;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class SubscriptionRequestDto {
    private final String nickName;
    private final String email;

    public SubscriptionRequestDto(Subscription subscription) {
        this.nickName = subscription.getNickname();
        this.email = subscription.getNickname();
    }
}
