package com.example.newnique.news.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class NewsHeartResponseDto {
    @JsonProperty("newsHeart")
    private int newsHeart;
    @JsonProperty("isNewsHeart")
    private boolean isNewsHeart;

    public NewsHeartResponseDto(int heartCount, boolean isNewsHeart) {
        this.newsHeart = heartCount;
        this.isNewsHeart = isNewsHeart;
    }
}
