package com.example.newnique.newsletter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Maildto {
    private String toAddress;
    private String title;
    private String message;

}
