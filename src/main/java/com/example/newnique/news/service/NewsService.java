package com.example.newnique.news.service;


import com.example.newnique.news.dto.NewsDetailsResponseDto;
import com.example.newnique.news.dto.NewsResponseDto;
import com.example.newnique.news.entity.News;
import com.example.newnique.news.repository.NewsRepository;
import com.example.newnique.subscription.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.support.PageableUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.newnique.global.exception.ErrorCode.NOT_FOUND_DATA;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsService {
    private final NewsRepository newsRepository;
    private final SubscriptionRepository subscriptionRepository;

    public static final String TOTAL_PAGE = "totalPages";
    public static final String NEWS_LIST = "totalPages";
    public static final String SUBSCRIBER_COUNT = "subscriberCount";
    public static final String TOTAL_NEWS_COUNT = "totalNewsCount";

    public Map<String, Object> getNews(int page, int size,
                                       String sortBy, boolean isAsc) {

        // 페이징 처리
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy, "title");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<News> newsList = newsRepository.findAll(pageable);


        List<NewsResponseDto> newsResponseDto = newsList.stream()
                .map(news ->
                        NewsResponseDto.builder()
                                .id(news.getId())
                                .title(news.getTitle())
                                .img(news.getImgUrl())
                                .category(news.getCategory())
                                .date(news.getNewsDate())
                                .build()
                )
                .collect(Collectors.toList());

        Map<String, Object> resposne = new HashMap<>();
        resposne.put(TOTAL_PAGE, newsList.getTotalPages());
        resposne.put(NEWS_LIST, newsResponseDto);
        resposne.put(SUBSCRIBER_COUNT, subscriptionRepository.count());

        return resposne;

    }

    public NewsDetailsResponseDto getNewsDetails(Long newsId) {

        News news = newsRepository.findById(newsId).orElseThrow(() ->
                new ResponseStatusException(NOT_FOUND_DATA.getStatus(), NOT_FOUND_DATA.formatMessage("뉴스"))
        );
        return NewsDetailsResponseDto.builder()
                .title(news.getTitle())
                .content(news.getContent())
                .img(news.getImgUrl())
                .category(news.getCategory())
                .date(news.getNewsDate())
                .heart(news.getHeartCount())
                .tag(news.getTag())
                .build();
    }

    public Map<String, Object> getNewsByCategory(String category, int page, int size, String sortBy, boolean isAsc) {

        // 페이징 처리
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy, "title");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<News> newsListByCategory = newsRepository.findAllByCategory(category, pageable);


        if (newsListByCategory.getContent().isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND_DATA.getStatus(), NOT_FOUND_DATA.formatMessage("뉴스 카테고리"));
        }


        Map<String, Object> response = new HashMap<>();
        List<NewsResponseDto> newsResponseDto = newsListByCategory.stream().map(news ->
                        NewsResponseDto.builder()
                                .id(news.getId())
                                .title(news.getTitle())
                                .img(news.getImgUrl())
                                .category(news.getCategory())
                                .date(news.getNewsDate())
                                .build())
                .collect(Collectors.toList());

        response.put(TOTAL_PAGE, newsListByCategory.getTotalPages());
        response.put(NEWS_LIST, newsResponseDto);

        return response;

    }

    public Map<String, Object> SearchNews(String keyword, int page,
                                          int size) {

        Pageable pageable = PageRequest.of(page, size);

        List<News> newsByKeyWordSearch = newsRepository.fullTextSearchNewsByKeyWordNativeVer(
                "+" + keyword + "*",
                pageable.getPageSize(),
                (int) pageable.getOffset()
        );


        List<NewsResponseDto> newsResponseDtoList = newsByKeyWordSearch.stream().map(news ->
                        NewsResponseDto.builder()
                                .id(news.getId())
                                .title(news.getTitle())
                                .img(news.getImgUrl())
                                .category(news.getCategory())
                                .date(news.getNewsDate())
                                .build())
                .collect(Collectors.toList());

        int totalNewsCount = newsRepository.countSearchNewsByKeyWordNativeVer("+" + keyword + "*");
        int totalPages = (int) Math.ceil((double) totalNewsCount / size);

        Map<String, Object> response = new HashMap<>();
        response.put(TOTAL_NEWS_COUNT, totalNewsCount);
        response.put(TOTAL_PAGE, totalPages);
        response.put(NEWS_LIST, newsResponseDtoList);

        return response;
    }


}
