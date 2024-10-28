package com.boot.petboard.controller;

import com.boot.petboard.domain.Article;
import com.boot.petboard.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ArticleController {
    @GetMapping("/create_article")
    public String create_article(Model model) {

        model.addAttribute("article", new Article());
        return "board_CRUD/create_article";
    }
    @GetMapping("/change_article")
    public String change_article() {
        return "board_CRUD/change_article";
    }

}
