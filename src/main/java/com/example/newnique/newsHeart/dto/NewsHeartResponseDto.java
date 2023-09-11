package com.example.newnique.newsHeart.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class NewsHeartResponseDto {

    private final int newsHeart;
    private final Boolean isNewsHeart;

}
