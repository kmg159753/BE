package com.example.newnique.news.repository;

import com.example.newnique.news.entity.News;
import com.example.newnique.news.entity.NewsHeart;
import com.example.newnique.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsHeartRepository extends JpaRepository<NewsHeart, Long> {
    NewsHeart findByHeartUserAndHeartNews(User user, News news);
}
