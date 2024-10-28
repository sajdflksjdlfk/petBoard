package com.boot.petboard.dto;

import com.boot.petboard.domain.ArticleComment;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record ArticleCommentResponse(
        Long id,
        String content,
        LocalDateTime createdAt,
        String email,
        String nickname,
        Long parentCommentId,
        Set<ArticleCommentResponse> childComments
) {
    public ArticleCommentResponse(ArticleComment articleComment) {
        this(articleComment.getId(), articleComment.getContent(), articleComment.getCreatedAt(),
                articleComment.getMember().getEmail(), articleComment.getMember().getNickname(),
                articleComment.getParentCommentId(), Set.copyOf(articleComment.getChildComments().stream()
                        .map(ArticleCommentResponse::new)
                        .collect(Collectors.toSet())));
    }
}
