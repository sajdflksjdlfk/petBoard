package com.boot.petboard.dto;

import com.boot.petboard.domain.Article;
import com.boot.petboard.domain.Member;
import lombok.Getter;

@Getter
public class ArticleResponse {

    private final Member member;
    private final String title;
    private final String content;

    public ArticleResponse(Article article) {
        this.title = article.getTitle();
        this.content = article.getContent();
        this.member = article.getMember();
    }
}