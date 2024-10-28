package com.boot.petboard.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Table
@Entity
public class ArticleComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_comment_id")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "article_id")
    private Article article;


    @JoinColumn(name = "email")
    @ManyToOne(optional = false)
    private Member member;


    @Column(name = "parent_comment_id")
    private Long parentCommentId;


    @OneToMany(mappedBy = "parentCommentId", cascade = CascadeType.REMOVE)
    private Set<ArticleComment> childComments = new LinkedHashSet<>();


    @Column(nullable = false, length = 500)
    private String content; // 본문




    public ArticleComment() {}

    private ArticleComment(Article article, Member member, Long parentCommentId, String content) {
        this.article = article;
        this.member = member;
        this.parentCommentId = parentCommentId;
        this.content = content;

    }

    public static ArticleComment of(Article article, Member member, String content) {
        return new ArticleComment(article, member, null, content);
    }

    public void addChildComment(ArticleComment child) {
        child.setParentCommentId(this.getId());
        this.getChildComments().add(child);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleComment)) return false;
        ArticleComment articleComment = (ArticleComment) o;
        return this.getId() != null && this.getId().equals(articleComment.getId());
    }

}
