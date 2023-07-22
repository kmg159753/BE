package com.example.newnique.news.dto;

import com.example.newnique.news.entity.Category;
import com.example.newnique.news.entity.News;
import lombok.Getter;

import java.util.List;

@Getter
public class NewsDetailsResponseDto {

    private String title;

    private String content;

    private String img;

    private Category category;

    private String date;

    private int heart;

    private List<Category> tags;

    public NewsDetailsResponseDto(News news) {
        this.title = news.getTitle();
        this.content = news.getContent();
        this.img = news.getImgUrl();
        this.category = news.getCategory();
        this.date = news.getDate();
        this.heart = news.getHeartCount();
        this.tags = news.getTag();
    }
}
