package com.example.newnique.newsHeart.controller;

import com.example.newnique.auth.security.UserDetailsImpl;
import com.example.newnique.newsHeart.dto.NewsHeartResponseDto;
import com.example.newnique.newsHeart.service.NewsHeartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/newsHeart")
public class NewsHeartController {
    private final NewsHeartService newsHeartService;

    @PostMapping("/{newsId}")
    public NewsHeartResponseDto createNewsHeart(@PathVariable Long newsId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return newsHeartService.createNewsHeart(newsId, userDetails.getUser());
    }

    @DeleteMapping("/{newsId}")
    public NewsHeartResponseDto deleteNewsHeart(@PathVariable Long newsId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return newsHeartService.deleteNewsHeart(newsId, userDetails.getUser());

    }
}
