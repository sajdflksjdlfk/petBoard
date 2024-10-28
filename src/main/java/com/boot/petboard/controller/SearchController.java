package com.boot.petboard.controller;

import com.boot.petboard.domain.Article;
import com.boot.petboard.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class SearchController {
    private final ArticleService articleService;

    //특정 검색시 발동
    //전체 검색(default)
    // Option = Title, Content, Tag 등을 고르고
    // Keyword 에 맞게 검색 한걸 반환
    // Pageable 추가해서 10개 마다 페이지 , 내림차순으로 정렬
    @GetMapping("/search_find")
    public String searchArticlesDefault(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                                        @RequestParam(value = "option", defaultValue = "title" ) String option,
                                        @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                        Model model){
        Page<Article> articles = articleService.searchArticles(keyword, option, pageable);
        model.addAttribute("articles", articles); //article 객체에 담긴정보
        model.addAttribute("keyword", keyword);   //입력한 검색 정보
        model.addAttribute("option", option);     //카테고리 선택한것
        return "search/search";
    }
}
