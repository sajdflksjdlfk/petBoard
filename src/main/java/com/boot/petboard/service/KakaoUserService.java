package com.boot.petboard.service;

import com.boot.petboard.domain.Member;
import com.boot.petboard.repository.KakaoUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoUserService {
    private final KakaoUserRepository userRepository;

    public void saveUserFromJson(String email) {
        Member member = new Member();
        member.setEmail(email);
        userRepository.save(member);
    }
}