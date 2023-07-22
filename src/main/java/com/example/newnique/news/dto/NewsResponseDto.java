package com.example.newnique.news.dto;

import com.example.newnique.news.entity.Category;
import com.example.newnique.news.entity.News;
import lombok.Getter;

@Getter
public class NewsResponseDto {

    private String title;

    private String img;

    private Category category;

    private String date;


    public NewsResponseDto(News news) {
        this.title = news.getTitle();
        this.img = news.getImgUrl();
        this.category = news.getCategory();
        this.date = news.getDate();
    }
}
