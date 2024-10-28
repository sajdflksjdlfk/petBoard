package com.boot.petboard.repository;

import com.boot.petboard.domain.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment,Long> {
    Optional<ArticleComment> findByArticle_IdAndId(Long articleId, Long id);

}
