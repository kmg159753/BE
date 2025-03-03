package com.example.newnique.news.dto;

import com.example.newnique.news.entity.News;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class NewsDetailsResponseDto {

    private final String title;

    private final String content;

    private final String img;

    private final String category;

    private final LocalDate date;

    private final int heart;

    private final String tag;

}
