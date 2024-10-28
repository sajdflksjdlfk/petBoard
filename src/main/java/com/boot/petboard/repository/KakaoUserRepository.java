package com.boot.petboard.repository;


import com.boot.petboard.domain.Member;
import com.boot.petboard.entity.KakaoUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KakaoUserRepository extends JpaRepository<Member, Long> {
    // 추가적인 메서드가 필요하다면 여기에 정의할 수 있습니다.
}