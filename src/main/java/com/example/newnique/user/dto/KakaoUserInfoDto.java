package com.example.newnique.user.dto;


import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class KakaoUserInfoDto {
    private final Long id;
    private final String nickname;
    private final String email;
}
