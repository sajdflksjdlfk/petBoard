package com.boot.petboard.controller;

import com.boot.petboard.domain.Article;
import com.boot.petboard.domain.ArticleComment;
import com.boot.petboard.dto.ArticleCommentRequest;
import com.boot.petboard.repository.ArticleRepository;
import com.boot.petboard.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ArticleCommentController {
    private final ArticleCommentService articleCommentService;
    private final ArticleRepository articleRepository;

    //댓글 작성
    @PostMapping("/comments/{articleId}")
    public ResponseEntity<Object> save(@PathVariable Long articleId, @RequestBody ArticleCommentRequest request, Principal principal) {
        articleCommentService.save(articleId, request, principal.getName());
        return ResponseEntity.noContent().build();
    }


    //댓글 삭제
    @DeleteMapping("/comments/{articleId}/{articleCommentId}")
    public ResponseEntity<Void> delete(@PathVariable long articleId, @PathVariable Long articleCommentId) {
        System.out.println("123123" + articleCommentId);
        articleCommentService.delete(articleId, articleCommentId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/comments/{articleId}/{parentCommentId}")
    public String saveReply(@PathVariable Long articleId,
                            @PathVariable Long parentCommentId,
                            @ModelAttribute ArticleCommentRequest request,
                            Principal principal,
                            RedirectAttributes redirectAttributes) {
        articleCommentService.saveReply(articleId, parentCommentId, request, principal.getName());
        // 대댓글 생성 후 해당 게시글의 보기 페이지로 리다이렉트
        redirectAttributes.addAttribute("articleId", articleId); // 리다이렉트 시 게시글 ID를 파라미터로 전달
        return "redirect:/article/" + articleId; // 리다이렉트할 주소
    }


    // 대댓글 읽어오기
    @GetMapping("/comments/{articleId}/{parentCommentId}/replies")
    public ResponseEntity<Object> getReplies(@PathVariable Long articleId, @PathVariable Long parentCommentId, Model model) {
        List<ArticleComment> replies = articleCommentService.findReplies(articleId, parentCommentId);
        model.addAttribute("replies", replies);
        // 대댓글을 보여주는 페이지로 이동
        return ResponseEntity.ok().build();
    }
    //대댓글 삭제
    @DeleteMapping("/comments/{articleId}/replies/{replyId}")
    public ResponseEntity<Void> deleteReply(@PathVariable long articleId,
                                            @PathVariable Long replyId) {
        articleCommentService.deleteReply(replyId);
        return ResponseEntity.noContent().build();
    }
}
