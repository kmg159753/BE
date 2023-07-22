package com.example.newnique.user.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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


    public User(String userEmail, String userPassword, String nickname) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.nickname = nickname;
    }

}