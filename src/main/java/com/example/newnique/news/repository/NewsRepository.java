package com.example.newnique.news.repository;

import com.example.newnique.news.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsRepository extends JpaRepository<News,Long> {

    Page<News> findAllByCategory(String category, Pageable pageable);
}
