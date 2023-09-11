package com.example.newnique.newsHeart.repository;

import com.example.newnique.news.entity.News;
import com.example.newnique.newsHeart.entity.NewsHeart;
import com.example.newnique.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsHeartRepository extends JpaRepository<NewsHeart, Long> {
    Optional<NewsHeart> findByUserAndNews(User user, News news);
}
