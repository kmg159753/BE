package com.example.newnique.newsletter.entity;

import com.example.newnique.newsletter.dto.SubscriptionRequestDto;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    private String nickname;




    public Subscription(SubscriptionRequestDto subscriptionRequestDto) {
        this.email = subscriptionRequestDto.getEmail();
        this.nickname = subscriptionRequestDto.getNickName();
    }
}

