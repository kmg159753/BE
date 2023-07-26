package com.example.newnique.news.service;


import com.example.newnique.exception.CategoryNotFoundException;
import com.example.newnique.exception.NewsNotFoundException;
import com.example.newnique.news.dto.NewsDetailsResponseDto;
import com.example.newnique.news.dto.NewsHeartResponseDto;
import com.example.newnique.news.dto.NewsResponseDto;
import com.example.newnique.news.entity.News;
import com.example.newnique.news.entity.NewsHeart;
import com.example.newnique.news.repository.NewsHeartRepository;
import com.example.newnique.news.repository.NewsRepository;
import com.example.newnique.newsletter.repository.SubscriptionRepository;
import com.example.newnique.user.entity.User;
import com.example.newnique.user.repository.UserRepository;
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
    private final NewsHeartRepository newsHeartRepository;
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

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
        resposne.put("subscriberCount", subscriptionRepository.count());

        return resposne;

    }

    public NewsDetailsResponseDto getNewsDetails(Long newsId) {

        News news = newsRepository.findById(newsId).orElseThrow(() ->
                new NewsNotFoundException("존재하지 않는 뉴스 입니다.")
        );
        return new NewsDetailsResponseDto(news);
    }

    public Map<String, Object> getNewsByCategory(String category, int page, int size, String sortBy, boolean isAsc) {

        // 페이징 처리
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<News> newsListByCategory = newsRepository.findAllByCategory(category, pageable);


        if(newsListByCategory.getContent().isEmpty()){
            throw new CategoryNotFoundException("존재하지 않는 카테고리입니다.");
        }


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

        List<News> newsListByCategory = newsRepository.fullTextSearchNewsByKeyWordNativeVer(
                keyword,
                pageable.getPageSize(),
                (int)pageable.getOffset(),
                sortBy,
                "Desc"
        );

        Map<String, Object> response = new HashMap<>();
        List<NewsResponseDto> newsResponseDtoList = newsListByCategory.stream().map(NewsResponseDto::new).collect(Collectors.toList());



        int totalNewsCount = newsRepository.countSearchNewsByKeyWordNativeVer(keyword);
        int totalPages = (int) Math.ceil((double) totalNewsCount / size);

        response.put("totalPages", totalPages);

        response.put("newsList", newsResponseDtoList);

        return response;
    }

    public Map<String, Object> SearchNewsBaSic(String keyword, int page,
                                               int size, String sortBy, boolean isAsc) {
        // 검색 시간 테스트를 위한 코드입니다.

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<News> searchNewsByKeyWord = newsRepository.searchNewsByKeyWord(keyword, pageable);

        Map<String, Object> response = new HashMap<>();
        List<NewsResponseDto> newsResponseDtoList = searchNewsByKeyWord.stream().map(NewsResponseDto::new).collect(Collectors.toList());

        response.put("totalPages", searchNewsByKeyWord.getTotalPages());
        response.put("newsList", newsResponseDtoList);

        return response;
    }

    public NewsHeartResponseDto getNewsHeart(Long newsId, String userEmail) {
        User loginUser = userRepository.findByUserEmail(userEmail).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        News news = newsRepository.findById(newsId).orElseThrow(() ->
                new NewsNotFoundException("존재하지 않는 뉴스입니다.")
        );

        NewsHeart existHeart = newsHeartRepository.findByHeartUserAndHeartNews(loginUser, news);
        if (existHeart == null) {
            NewsHeart newsHeart = new NewsHeart(loginUser, news);
            news.increaseHeartCount();
            newsHeartRepository.save(newsHeart);
        } else {
            news.decreaseHeartCount();
            newsHeartRepository.delete(existHeart);
        }
        return new NewsHeartResponseDto(news.getHeartCount());
    }
}