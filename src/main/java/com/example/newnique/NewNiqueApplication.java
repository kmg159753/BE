package com.example.newnique;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication (exclude = SecurityAutoConfiguration.class)
public class NewNiqueApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewNiqueApplication.class, args);
    }

}
