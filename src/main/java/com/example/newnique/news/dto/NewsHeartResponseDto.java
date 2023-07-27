package com.example.newnique.news.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class NewsHeartResponseDto {

    private int newsHeart;
    private Boolean isNewsHeart ;

    public NewsHeartResponseDto(int heartCount, boolean isNewsHeart) {
        this.newsHeart = heartCount;
        this.isNewsHeart = isNewsHeart;
    }
}
