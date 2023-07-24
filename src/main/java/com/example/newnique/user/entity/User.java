package com.example.newnique.user.entity;


import com.example.newnique.news.entity.NewsHeart;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true,nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String userPassword;

    @Column(nullable = false)
    private String nickname;

    @OneToMany(mappedBy = "heartUser", cascade = CascadeType.REMOVE)
    private List<NewsHeart> userHearts;

    public User(String userEmail, String userPassword, String nickname) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.nickname = nickname;
    }

}