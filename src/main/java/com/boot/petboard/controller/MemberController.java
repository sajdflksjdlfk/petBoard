package com.boot.petboard.controller;

import com.boot.petboard.domain.Member;

import com.boot.petboard.dto.MemberFormDto;
import com.boot.petboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    //회원가입창으로 이동
    @GetMapping("/create_account")
    public String memberSave() {
        return "member/create_account";
    }

    //중복확인 매서드
    @PostMapping(value = "/members/checkDuplicate")
    @ResponseBody
    public ResponseEntity<?> checkDuplicate(@RequestBody Map<String, String> requestBody) {     //json형태로 넘어온 값을 map으로 받음

        String nickname = requestBody.get("nickname");      //map의 값을 변수에 할당하여, 넘어온 값이 무엇인지 확인 
        String email = requestBody.get("email");
        
        //넘어온 값에 맞는 로직을 실행
        if(nickname != null || email == null) {
            System.out.println(nickname+"닉네임은?");
            boolean duplicatedMember = memberService.validateDuplicateMember(nickname, false);
            return ResponseEntity.ok(duplicatedMember);

        }else{
            System.out.println(email+"이메일은?");
            boolean duplicatedMember = memberService.validateDuplicateMember(email, true);
            return ResponseEntity.ok(duplicatedMember);
        }
    }

    //회원 정보를 받아와 유효성 검사를 하고, 유효하다면 메인화면으로, 유효하지 않다면 회원가입창으로 이동
    @PostMapping("/members/new")
    public String memberForm(MemberFormDto memberFormDto, Model model) {

            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);

        return "redirect:/login";
    }

    @GetMapping(value = "/login")
    public String loginPage(){
        return "member/login";
    }

    @GetMapping(value = "/logout")
    public String logoutMember(){
        return "member/login";
    }

}
