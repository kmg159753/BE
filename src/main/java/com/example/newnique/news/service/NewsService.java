package com.example.newnique.news.service;

import com.example.newnique.news.dto.NewsDetailsResponseDto;
import com.example.newnique.news.dto.NewsResponseDto;
import com.example.newnique.news.entity.News;
import com.example.newnique.news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsService {
    private final NewsRepository newsRepository;
    public Map<String, Object> getNews(int page, int size,
                                               String sortBy, boolean isAsc) {

        // 페이징 처리
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<News> newsList = newsRepository.findAll(pageable);


        List<NewsResponseDto> newsResponseDto = newsList.stream()
                .map(NewsResponseDto::new)
                .collect(Collectors.toList());

        Map<String, Object> resposne = new HashMap<>();
        resposne.put("totalPages", newsList.getTotalPages());
        resposne.put("newsList", newsResponseDto);

        return resposne;

    }

    public NewsDetailsResponseDto getNewsDetails(Long newsId) {

        News news = newsRepository.findById(newsId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 기사 입니다.")
        );
        return new NewsDetailsResponseDto(news);
    }

    public Map<String, Object> getNewsByCategory(String category, int page, int size, String sortBy, boolean isAsc) {

        // 페이징 처리
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<News> newsListByCategory = newsRepository.findAllByCategory(category, pageable);

        Map<String, Object> response = new HashMap<>();
        List<NewsResponseDto> newsResponseDto = newsListByCategory.stream().map(NewsResponseDto::new).collect(Collectors.toList());

        response.put("totalPages", newsListByCategory.getTotalPages());
        response.put("newsList", newsResponseDto);

        return response;

    }

    public Map<String, Object> SearchNews(String keyword, int page,
                                          int size, String sortBy, boolean isAsc) {

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<News> searchedNewsByCategory = newsRepository.searchNewsByCategory(keyword, pageable);

        Map<String, Object> response = new HashMap<>();
        List<NewsResponseDto> newsResponseDtoList = searchedNewsByCategory.stream().map(NewsResponseDto::new).collect(Collectors.toList());

        for (News news : searchedNewsByCategory) {
            log.info(news.getContent());
        }

        response.put("totalPages", searchedNewsByCategory.getTotalPages());
        response.put("newsList", newsResponseDtoList);

        return response;
    }
}
