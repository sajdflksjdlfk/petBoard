package com.boot.petboard.domain;

import com.boot.petboard.domain.constant.Category;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.dialect.function.array.ArrayToStringFunction;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Table
@Entity
@AllArgsConstructor
public class Article extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="article_id")
    private Long id;

    @JoinColumn(name = "email")
    @ManyToOne
    private Member member; // 유저 정보 (ID)

    @Column(nullable = false)
    private String title; // 제목

    @Column(nullable = false, length = 10000)
    private String content; // 본문

    private String category;

    @Getter
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ArticleComment> articleComments;


    public Article() {

    }


    @Builder
    public Article(Member member, String title, String content, String category) {
        this.member = member;
        this.title = title;
        this.content = content;
        this.category = category;

    }
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return this.getId() != null && this.getId().equals(article.getId());
    }

}
