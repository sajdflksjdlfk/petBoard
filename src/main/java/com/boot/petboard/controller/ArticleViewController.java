package com.boot.petboard.controller;

import com.boot.petboard.domain.Article;
import com.boot.petboard.dto.ArticleResponse;
import com.boot.petboard.repository.ArticleRepository;
import com.boot.petboard.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class ArticleViewController {

    private final ArticleService articleService;
    private final ArticleRepository articleRepository;


    @GetMapping("/article/{id}")
    public String viewArticle(@PathVariable Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName(); // 현재 사용자 이메일

        Article article = articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid article Id:" + id));
        boolean isAuthor = currentUserEmail.equals(article.getMember().getEmail());
        System.out.println(isAuthor);
        model.addAttribute("article", article);
        model.addAttribute("comments", article.getArticleComments());
        model.addAttribute("isAuthor", isAuthor);

        System.out.println("2222==>" + article.getTitle());

        return "board_CRUD/view_article"; // Thymeleaf 템플릿 이름
    }
}
