package com.example.newnique.newsHeart.service;

import com.example.newnique.news.entity.News;
import com.example.newnique.news.repository.NewsRepository;
import com.example.newnique.newsHeart.dto.NewsHeartResponseDto;
import com.example.newnique.newsHeart.entity.NewsHeart;
import com.example.newnique.newsHeart.repository.NewsHeartRepository;
import com.example.newnique.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static com.example.newnique.global.exception.ErrorCode.DUPLICATE_DATA;
import static com.example.newnique.global.exception.ErrorCode.NOT_FOUND_DATA;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsHeartService {

    private final NewsRepository newsRepository;
    private final NewsHeartRepository newsHeartRepository;


    public NewsHeartResponseDto createNewsHeart(Long newsId, User user) {

        News news = newsRepository.findById(newsId).orElseThrow(() ->
                new ResponseStatusException(NOT_FOUND_DATA.getStatus(), NOT_FOUND_DATA.formatMessage("뉴스"))
        );

        if (newsHeartRepository.findByUserAndNews(user, news).isPresent()) {
            throw new ResponseStatusException(DUPLICATE_DATA.getStatus(), DUPLICATE_DATA.formatMessage("좋아요한 뉴스"));
        }

        news.increaseHeartCount();

        newsHeartRepository.save(
                NewsHeart.builder()
                        .news(news)
                        .user(user)
                        .build()
        );

        return NewsHeartResponseDto.builder()
                .newsHeart(news.getHeartCount())
                .isNewsHeart(true)
                .build();

    }

    public NewsHeartResponseDto deleteNewsHeart(Long newsId, User user) {

        News news = newsRepository.findById(newsId).orElseThrow(() ->
                new ResponseStatusException(NOT_FOUND_DATA.getStatus(), NOT_FOUND_DATA.formatMessage("뉴스"))
        );

        NewsHeart newsHeart = newsHeartRepository.findByUserAndNews(user, news).orElseThrow(() ->
                new ResponseStatusException(NOT_FOUND_DATA.getStatus(), NOT_FOUND_DATA.formatMessage("좋아요한 뉴스"))
        );

        news.decreaseHeartCount();

        newsHeartRepository.delete(newsHeart);

        return NewsHeartResponseDto.builder()
                .newsHeart(news.getHeartCount())
                .isNewsHeart(false)
                .build();
    }
}
