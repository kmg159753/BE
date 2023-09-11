package com.example.newnique.subscription.entity;

import com.example.newnique.subscription.dto.SubscriptionRequestDto;
import jakarta.persistence.Entity;
import lombok.*;
import jakarta.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    private String nickname;

}

