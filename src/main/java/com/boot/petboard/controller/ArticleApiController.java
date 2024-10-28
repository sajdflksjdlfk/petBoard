package com.boot.petboard.controller;

import com.boot.petboard.domain.Article;
import com.boot.petboard.dto.AddArticleRequestDto;

import com.boot.petboard.dto.ArticleResponse;
import com.boot.petboard.dto.UpdateArticleRequest;
import com.boot.petboard.service.ArticleService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
public class ArticleApiController {
    private final ArticleService articleService;
    private final HttpSession session;

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequestDto request, Principal principal, Authentication authentication  ) {
        Article savedArticle = null;
        if (principal.getName() == null) {
            String userEmail = (String) session.getAttribute("userEmail");
            String regex = "\\b[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\\b";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(userEmail);
            if(matcher.find()){
                userEmail = matcher.group();
            }
            System.out.println("userEmail : " + userEmail);
            savedArticle = articleService.save(request, userEmail);
        }
        else{
            savedArticle = articleService.save(request, principal.getName());
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id) {
        Article article = articleService.findById(id);

        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id) {
        articleService.delete(id);

        return ResponseEntity.ok()
                .build();
    }
}
