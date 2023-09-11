package com.example.newnique.subscription.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class MailSendDto {
    private final String toAddress;
    private final String title;
    private final String message;

}
