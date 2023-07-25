package com.example.newnique.newsletter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Maildto {
    private String toAddress;
    private String title;
    private String message;

}
