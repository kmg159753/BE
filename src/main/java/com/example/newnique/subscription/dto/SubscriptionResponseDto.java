package com.example.newnique.subscription.dto;


import com.example.newnique.subscription.entity.Subscription;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class SubscriptionResponseDto {
    private final String nickName;
    private final String email;
}
