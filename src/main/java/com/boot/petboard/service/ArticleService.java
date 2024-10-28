package com.boot.petboard.service;

import com.boot.petboard.domain.Article;
import com.boot.petboard.domain.Member;
import com.boot.petboard.dto.AddArticleRequestDto;

import com.boot.petboard.dto.UpdateArticleRequest;
import com.boot.petboard.repository.ArticleRepository;
import com.boot.petboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

//    public Article save(AddArticleRequestDto request, String userName) {
//
//        //articleRepository.findByEmail();
//        return articleRepository.save(request.toEntity(userName));
//    }


    public Article save(AddArticleRequestDto request, String userName) {

        System.out.println("게시글 내용" + request.getContent());
        //1. article 객체를 만든다(멤버가 없는)
        //2. 현재 유저의 email로 해당 유저의 객체를 찾는다
        //3. article에 추가한다
        //4. 완성된 article객체를 db에 저장한다
        Member member = memberRepository.findByEmail(userName);
        Article article = request.toEntity(userName);
        article.setMember(member);
        article.setCreatedAt(LocalDateTime.now());

        System.out.println("게시글 내용" + article.getContent());
        System.out.println("카테고리" + article.getCategory());
        System.out.println("로그인 멤버의 이메일" + member.getEmail());
        return articleRepository.save(article);
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public Article findById(long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }

    public void delete(long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        articleRepository.delete(article);
    }

    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        article.update(request.getTitle(), request.getContent());

        return article;
    }



    // 랜덤으로 게시글 가져오기
    public Page<Article> getRandomArticles(Pageable pageable) {
        return articleRepository.findRandomArticles(pageable);
    }

    public Page<Article> searchArticles(String keyword, String option, Pageable pageable) {
        switch (option) { //카테고리(옵션) 분류 한곳에서 포함된 keyword로 찾기
            case "title":
                return articleRepository.findByTitleContaining(keyword, pageable);
            case "content":
                return articleRepository.findByContentContaining(keyword, pageable);
//            case "category":
//                return articleRepository.findByCategoryContaining(keyword, pageable);
            default:
                return articleRepository.findAll(pageable);
        }

    }
    public List<Article> getAllArticles() {
        return articleRepository.findAll(); // 모든 Articles 를 반환
    }


    public Article findById(Long id) { //페이징해서 나온 것들 하이퍼 링크넣어주기
        return articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid article ID: " + id));
    }


    public Page<Article> findAllArticlesByCategory(String category, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return articleRepository.findByCategoryOrderByCreatedAtDesc(category, pageRequest);
    }

}