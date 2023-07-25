package com.example.newnique.newsletter.repository;

import com.example.newnique.newsletter.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {

}
