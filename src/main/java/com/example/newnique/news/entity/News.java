package com.example.newnique.news.entity;


import com.example.newnique.global.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
public class News extends Timestamped {

    @Id
    @GeneratedValue
    private Long Id;

    @Column
    private String title;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    @Column
    private String imgUrl;

    @Column
    private LocalDate newsDate;

    @Column
    private String category;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String newsSummary;

    @Column
    private String tag;

    @Column
    private int heartCount;

    @OneToMany(mappedBy = "heartNews", cascade = CascadeType.REMOVE)
    private List<NewsHeart> newsHearts;



    public News(String title, String content, String imgUrl, LocalDate NewsDate, String category,String newsSummary,String tags) {

        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
        this.newsDate = NewsDate;
        this.category = category;
        this.newsSummary = newsSummary;
        this.tag = tags;

    }

    public void increaseHeartCount() {
        this.heartCount += 1;
    }
    public void decreaseHeartCount() {
        this.heartCount -= 1;
    }

}
