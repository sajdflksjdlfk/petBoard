package com.boot.petboard.dto;

import com.boot.petboard.domain.Article;
import com.boot.petboard.domain.BaseEntity;
import com.boot.petboard.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddArticleRequestDto {
    private String title;

    private String content;

    private String category;



    public Article toEntity(String author) {
        return Article.builder()
                .title(title)
                .content(content)
                .category(category)
                .build();
    }
}