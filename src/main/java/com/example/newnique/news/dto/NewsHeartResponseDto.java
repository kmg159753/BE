package com.example.newnique.news.dto;

import lombok.Getter;

@Getter
public class NewsHeartResponseDto {
    private int NewsHeart;

    public NewsHeartResponseDto(int heartCount) {
        this.NewsHeart = heartCount;
    }
}
