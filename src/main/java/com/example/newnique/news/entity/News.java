package com.example.newnique.news.entity;


import com.example.newnique.global.entity.Timestamped;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    public void increaseHeartCount() {
        this.heartCount += 1;
    }

    public void decreaseHeartCount() {
        this.heartCount -= 1;
    }

}
