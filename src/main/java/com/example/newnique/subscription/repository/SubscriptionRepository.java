package com.example.newnique.subscription.repository;

import com.example.newnique.subscription.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {
    long count();
}
