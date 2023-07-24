package com.example.newnique.news.controller;

import com.example.newnique.news.dto.NewsDetailsResponseDto;
import com.example.newnique.news.dto.NewsResponseDto;
import com.example.newnique.news.service.NewsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
@Slf4j
public class NewsController {

    private final NewsService newsService;


    @GetMapping()
    public ResponseEntity<Map<String, Object>> getNews(@RequestParam("page") int page,
                                                               @RequestParam("size") int size,
                                                               @RequestParam("sortBy") String sortBy,
                                                               @RequestParam("isAsc") boolean isAsc
    ){

        Map<String, Object> newsResponseDtoList = newsService.getNews(
            page - 1,
            size,
            sortBy,
            isAsc
        );

        return ResponseEntity.ok(newsResponseDtoList);
    }

    @GetMapping("/category")
    public ResponseEntity<Map<String, Object>> getNewsByCategory(
            @RequestParam("category") String category,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc
    ){

        Map<String, Object> newsResponseDtoList = newsService.getNewsByCategory(
                category,
                page - 1,
                size,
                sortBy,
                isAsc
        );

        return ResponseEntity.ok(newsResponseDtoList);
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> SearchNews(
            @RequestParam("keyword") String keyword,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc
    ){

        Map<String, Object> newsResponseDtoList = newsService.SearchNews(
                keyword,
                page - 1,
                size,
                sortBy,
                isAsc
        );

        return ResponseEntity.ok(newsResponseDtoList);
    }

    @GetMapping("/{newsId}")
    public ResponseEntity<NewsDetailsResponseDto> getNewsDetails(@PathVariable Long newsId){
        NewsDetailsResponseDto newsDetails = newsService.getNewsDetails(newsId);

        return ResponseEntity.ok(newsDetails);
    }
}
