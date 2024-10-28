package com.boot.petboard.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Controller
@RequestMapping("/kakao")
public class KakaoLoginController {
    @GetMapping("/login")
    public String login(){
        return "/member/login";
    }


    @GetMapping("/main") //메인 페이지로 이동
    public String loginPage(HttpSession session, Model model, Principal principal) {
//        String userId = (String) session.getAttribute("userId");
        String userEmail = (String) session.getAttribute("userEmail");


        String regex = "\\b[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(userEmail);
        // 이메일 주소를 찾았을 때만 값을 추출하여 사용
        if (matcher.find()) {
            userEmail = matcher.group();
        }
        System.out.println("principalKakaoLogin : " + userEmail);

        //세션정보 확인
//        model.addAttribute("userId", userId);
        model.addAttribute("userEmail", userEmail);

        return "mainPage";
    }



}
