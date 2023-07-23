package com.example.newnique.news.dto;

import com.example.newnique.news.entity.News;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class NewsResponseDto {

    private String title;

    private String img;

    private String category;

    private LocalDate date;


    public NewsResponseDto(News news) {
        this.title = news.getTitle();
        this.img = news.getImgUrl();
        this.category = news.getCategory();
        this.date = news.getNewsDate();
    }
}
