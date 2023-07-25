package com.example.newnique.newsletter.controller;

import com.example.newnique.newsletter.dto.SubscriptionRequestDto;
import com.example.newnique.newsletter.dto.SubscriptionResponseDto;
import com.example.newnique.newsletter.service.SubscriptionService;
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
    public ResponseEntity<SubscriptionResponseDto> createSub(@RequestBody SubscriptionRequestDto subscriptionRequestDto) {
        SubscriptionResponseDto subscriptionResponseDto = new SubscriptionResponseDto(subscriptionService.createSub(subscriptionRequestDto));

        return ResponseEntity.ok(subscriptionResponseDto);

    }


}
