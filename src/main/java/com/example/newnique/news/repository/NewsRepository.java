package com.example.newnique.news.repository;

import com.example.newnique.news.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NewsRepository extends JpaRepository<News,Long> {

    News findTopByOrderByIdDesc();

    Page<News> findAllByCategory(String category, Pageable pageable);

    @Query("select n FROM News n WHERE n.title like %:keyword% or n.content like %:keyword% or n.category like %:keyword%")
    Page<News> searchNewsByCategory(@Param("keyword") String keyword,Pageable pageable);
}
