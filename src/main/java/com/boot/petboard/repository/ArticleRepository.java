package com.boot.petboard.repository;

import com.boot.petboard.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findByTitleContaining(String title, Pageable pageable);          //Title 에 포함되는 글 모두 찾기
    Page<Article> findByContentContaining(String content, Pageable pageable);      //Content 에 포함되는 글 모두 찾기
//    Page<Article> findByCategoryContaining(String category, Pageable pageable);    //태그 전용인데 나중에 고칠거임


    Page<Article> findAll(Pageable pageable);  //전체 받기

    List<Article> findByCategory(String category);

    // 랜덤으로 게시글 가져오기
    @Query(value = "SELECT a FROM Article a ORDER BY function('RAND')")
    Page<Article> findRandomArticles(Pageable pageable);


    Page<Article> findByCategoryOrderByCreatedAtDesc(String category, Pageable pageable);

}
