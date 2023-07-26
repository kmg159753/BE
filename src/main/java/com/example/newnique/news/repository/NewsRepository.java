package com.example.newnique.news.repository;

import com.example.newnique.news.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NewsRepository extends JpaRepository<News,Long> {

    Page<News> findAllByCategory(String category, Pageable pageable);

    @Query("select n FROM News n WHERE n.title like %:keyword% or n.content like %:keyword% or n.category like %:keyword%")
    Page<News> searchNewsByKeyWord(@Param("keyword") String keyword, Pageable pageable);

    @Query(
            value =
                    "SELECT * FROM news WHERE MATCH(title,content,category) AGAINST (:keyword) "+
                            "LIMIT :limit OFFSET :offset", nativeQuery = true
    )
    List<News> fullTextSearchNewsByKeyWordNativeVer(
            @Param("keyword") String keyword,
            @Param("limit") int limit,
            @Param("offset") int offset
    );

    @Query(
            value = "SELECT COUNT(*) FROM news WHERE MATCH(title,content,category) AGAINST (:keyword)",
            nativeQuery = true
    )
    int countSearchNewsByKeyWordNativeVer(
            @Param("keyword") String keyword
    );

    News findTopByOrderByIdDesc();

}