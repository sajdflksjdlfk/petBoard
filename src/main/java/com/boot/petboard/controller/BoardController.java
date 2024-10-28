package com.boot.petboard.controller;

import com.boot.petboard.domain.Article;
import com.boot.petboard.repository.ArticleRepository;
import com.boot.petboard.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final ArticleService articleService;


    @GetMapping("/freeBoard")
    public String freeBoard(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        // 게시글 리스트 가져오기
        Page<Article> articlePage = articleService.findAllArticlesByCategory("자유게시판", page, size);

        // 현재 페이지와 총 페이지 수
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", articlePage.getTotalPages());

        // 게시글 목록
        model.addAttribute("category", "자유게시판");
        model.addAttribute("articles", articlePage.getContent());

        return "category/freeBoard";
    }




    @GetMapping("/QnA")
    public String QnA(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        // 게시글 리스트 가져오기
        Page<Article> articlePage = articleService.findAllArticlesByCategory("1", page, size);

        // 현재 페이지와 총 페이지 수
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", articlePage.getTotalPages());

        // 게시글 목록
        model.addAttribute("category", "QnA");
        model.addAttribute("articles", articlePage.getContent());

        return "category/QnA";
    }

    @GetMapping("/UCC")
    public String UCC(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        // 게시글 리스트 가져오기
        Page<Article> articlePage = articleService.findAllArticlesByCategory("2", page, size);

        // 현재 페이지와 총 페이지 수
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", articlePage.getTotalPages());

        // 게시글 목록
        model.addAttribute("category", "UCC");
        model.addAttribute("articles", articlePage.getContent());

        return "category/UCC";
    }

    @GetMapping("/guestBook")
    public String guestBook(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        // 게시글 리스트 가져오기
        Page<Article> articlePage = articleService.findAllArticlesByCategory("3", page, size);

        // 현재 페이지와 총 페이지 수
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", articlePage.getTotalPages());

        // 게시글 목록
        model.addAttribute("category", "guestBook");
        model.addAttribute("articles", articlePage.getContent());

        return "category/guestBook";
    }

//    private final ArticleRepository articleRepository;
//
//    //실행은 이걸로
//    @GetMapping("/free_Board")
//    public String freeBoard1(Model model) {
//
//        List<Article> articles = articleRepository.findByCategory("자유게시판");
//        // 자유게시판에 해당하는 값들만 가져옴
//
//        model.addAttribute("articles", articles);
//
//        return "category/freeBoard";
//    }
//
//    //비동기처리하는 매서드
//    @GetMapping("/freeBoard")
//    public String freeBoard(Model model) {
//
//        List<Article> articles = articleRepository.findByCategory("자유게시판");
//        // 자유게시판에 해당하는 값들만 가져옴
//
//        model.addAttribute("category", "자유게시판");
//        model.addAttribute("articles", articles);
//
//        return "category/Board";
//    }
//
//
//    @GetMapping("/QnA")
//    public String QnA(Model model) {
//
//        List<Article> articles = articleRepository.findByCategory("1");
//        // 자유게시판에 해당하는 값들만 가져옴
//        model.addAttribute("category", "QnA");
//        model.addAttribute("articles", articles);
//
//
//        return "category/Board";
//    }
//
//    @GetMapping("/UCC")
//    public String UCC(Model model) {
//        List<Article> articles = articleRepository.findByCategory("2");
//        // 자유게시판에 해당하는 값들만 가져옴
//
//        model.addAttribute("category", "UCC");
//        model.addAttribute("articles", articles);
//
//        return "category/Board";
//    }
//
//    @GetMapping("/guestBook")
//    public String guestBook(Model model) {
//        List<Article> articles = articleRepository.findByCategory("3");
//        // 자유게시판에 해당하는 값들만 가져옴
//
//
//        model.addAttribute("category", "방명록");
//        model.addAttribute("articles", articles);
//        return "category/Board";
//    }
}
