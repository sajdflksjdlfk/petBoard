package com.boot.petboard.repository;


import com.boot.petboard.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);
    Member findByNickname(String nickname);



}
