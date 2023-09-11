package com.example.newnique.subscription.controller;

import com.example.newnique.subscription.dto.SubscriptionRequestDto;
import com.example.newnique.subscription.dto.SubscriptionResponseDto;
import com.example.newnique.subscription.service.SubscriptionService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;


    @PostMapping("/sub")
    public SubscriptionResponseDto createSub(@RequestBody SubscriptionRequestDto subscriptionRequestDto) throws MessagingException {
        return subscriptionService.createSub(subscriptionRequestDto);

    }


}
