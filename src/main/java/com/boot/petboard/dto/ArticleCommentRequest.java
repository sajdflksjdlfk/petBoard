package com.boot.petboard.dto;

import com.boot.petboard.domain.Article;
import com.boot.petboard.domain.ArticleComment;
import com.boot.petboard.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleCommentRequest {
    private Long commentId;
    private Long parentCommentId;
    private String content;
    private Member member;
    private Article article;

    public ArticleCommentRequest(Long commentId, Long parentCommentId, String content, Member member, Article article) {
        this.commentId = commentId;
        this.parentCommentId = parentCommentId;
        this.content = content;
        this.member = member;
        this.article = article;
    }

    public ArticleComment toEntity() {
        ArticleComment articleComment = new ArticleComment();
        articleComment.setArticle(this.article);
        articleComment.setMember(this.member);
        articleComment.setContent(this.content);

        // 대댓글인 경우에만 부모 댓글 ID 설정
        if (this.parentCommentId != null) {
            articleComment.setParentCommentId(this.parentCommentId);
        }

        return articleComment;
    }
}

