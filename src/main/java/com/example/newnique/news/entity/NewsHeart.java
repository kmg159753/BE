package com.example.newnique.news.entity;


import com.example.newnique.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class NewsHeart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "heartUser_id")
    private User heartUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "heartNews_id")
    private News heartNews;

    public NewsHeart(User heartUser, News heartNews) {
        this.heartUser = heartUser;
        this.heartNews = heartNews;
    }
}
