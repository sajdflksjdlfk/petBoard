package com.boot.petboard.service;

import com.boot.petboard.domain.Member;
import com.boot.petboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    //중복검사 후 회원 저장하는 매서드
    public Member saveMember(Member member){
        return memberRepository.save(member);
    }

    public Member findMemberByEmail(String email){
        return  memberRepository.findByEmail(email);
    }

    //중복회원 검사
    public boolean validateDuplicateMember(String duplicateCheck, boolean isEmail) {
        System.out.println(duplicateCheck+"이메일일까? 닉네임일까?");

        Member findMember = null;
        if (isEmail) {
            findMember = memberRepository.findByEmail(duplicateCheck);
        } else {
            findMember = memberRepository.findByNickname(duplicateCheck);
        }

        if (findMember == null) {
            System.out.println("사용가능한 아이디입니다 (닉네임)");
            return true;    // 사용가능한 값
        } else {
            System.out.println("사용 불가능한 아이디입니다 (닉네임)");
            return false;   //사용 불가능한 값
        }
    }



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);
        if(member==null) {
            throw new UsernameNotFoundException(email);
        }
        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .build();
    }
}