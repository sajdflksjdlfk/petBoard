package com.boot.petboard.controller;

import com.boot.petboard.domain.Article;
import com.boot.petboard.service.ArticleService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Controller
@RequiredArgsConstructor
public class MainController {
    @Autowired
    private final ArticleService articleService;

    @GetMapping(value = "/")
    public String mainPage(Model model , HttpSession session , Principal principal) {
        //article 객체에 담긴정보
        Page<Article> articles = articleService.searchArticles( "", "title", Pageable.ofSize(5));
        model.addAttribute("articles", articles);

        // 랜덤 인기글
        Page<Article> articles2 = articleService.getRandomArticles(Pageable.ofSize(5));
        model.addAttribute("articles1", articles2);

        //아이디 메인화면에 뛰우기 용도
        String userEmail = null;
        userEmail = (String) session.getAttribute("userEmail");

        if (userEmail != null){
            String regex = "\\b[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\\b";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(userEmail);
            if (matcher.find()) {
                userEmail = matcher.group();
            }
        }
        else if(principal != null){
            userEmail  = principal.getName();
        }else {
            userEmail= "Login 을 해주세요";
        }
        System.out.println("메인 : " + userEmail);
        model.addAttribute("userEmail",userEmail);
        return "mainPage";
    }


    @GetMapping("personal_info")
    public String personalPage(){
        return "/member/personal_info";
    }
}

