package com.example.newnique.news.service;

import com.example.newnique.news.dto.NewsDetailsResponseDto;
import com.example.newnique.news.dto.NewsResponseDto;
import com.example.newnique.news.entity.News;
import com.example.newnique.news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;
    public Page<NewsResponseDto> getNews(int page, int size,
                                               String sortBy, boolean isAsc) {

        // 페이징 처리
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<News> newsList = newsRepository.findAll(pageable);

        return newsList.map(NewsResponseDto::new);
    }

    public NewsDetailsResponseDto getNewsDetails(Long newsId) {

        News news = newsRepository.findById(newsId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 기사 입니다.")
        );
        return new NewsDetailsResponseDto(news);
    }
}
