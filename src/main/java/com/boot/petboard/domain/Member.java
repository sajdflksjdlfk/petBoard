package com.boot.petboard.domain;

import com.boot.petboard.domain.constant.Role;
import com.boot.petboard.dto.MemberFormDto;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Table
@Entity
public class Member {
    @Id
    @Column(length = 100)
    private String email;

    @Column(length = 100,unique = true)
    private String nickname;

    private String password;

    private LocalDateTime createdAt;

    //생성자
    private Member(String email, String nickname, String password, Role role,LocalDateTime createdAt ) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.createdAt = LocalDateTime.now();

    }

    public Member() {

    }

    public Member(String name) {
    }

    //입력받은 정보로 Member객체 생성
    public static Member of(String email, String nickname, String password,LocalDateTime createdAt) {
        return new Member(email, nickname,password ,null, createdAt);
    }


    //dto->엔티티 객체로 변환하는 매서드
    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        LocalDateTime now = LocalDateTime.now();
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        return Member.of(memberFormDto.getEmail(), memberFormDto.getNickname(), password, now);
    }



}




