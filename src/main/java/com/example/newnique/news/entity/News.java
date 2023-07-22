package com.example.newnique.news.entity;


import com.example.newnique.global.Timestamped;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.List;


@Entity
@Getter
public class News extends Timestamped {

    @Id
    @GeneratedValue
    private Long Id;

    @Column
    private String title;

    @Column(length = 2000)
    private String content;

    @Column
    private String imgUrl;

    @Column
    private String date;

    @Column
    private Category category;

    @Column
    private List<Category> tag;

    @Column
    private int heartCount;




}
