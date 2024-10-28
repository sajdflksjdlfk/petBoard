package com.boot.petboard.service;

import com.boot.petboard.domain.Article;
import com.boot.petboard.domain.ArticleComment;
import com.boot.petboard.domain.Member;
import com.boot.petboard.dto.ArticleCommentRequest;
import com.boot.petboard.repository.ArticleCommentRepository;
import com.boot.petboard.repository.ArticleRepository;
import com.boot.petboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ArticleCommentService {
    private final ArticleCommentRepository articleCommentRepository;
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

    //댓글 추가 메서드
    @Transactional(readOnly = false)
    public void save(Long articleId, ArticleCommentRequest request, String username) {
        // 게시글이 존재하는지 확인
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다. " + articleId));

        // 댓글 작성자 정보 등록
        Member member = memberRepository.findByEmail(username);
        request.setMember(member);

        ArticleComment comment = request.toEntity();

        // 부모 댓글이 있는 경우 연결
        if (request.getParentCommentId() != null) {
            ArticleComment parentComment = articleCommentRepository.findById(request.getParentCommentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 댓글이 존재하지 않습니다."));

            parentComment.addChildComment(comment);
            articleCommentRepository.save(parentComment);
        } else {
            // 부모 댓글이 없는 경우 게시글과 댓글을 연결
            comment.setArticle(article);
            articleCommentRepository.save(comment);
        }

    }

    @Transactional(readOnly = true)
    public List<ArticleComment> findAll(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id: " + id));
        List<ArticleComment> comments = article.getArticleComments();
        // 로그 추가
        System.out.println("댓글 리스트: " + comments);
        return comments;
    }

    // 댓글 삭제 메서드
    @Transactional
    public void delete(Long articleId, Long id) {
        // 해당 댓글 찾기
        ArticleComment articleComment = articleCommentRepository.findByArticle_IdAndId(articleId, id)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id=" + id));

        // 해당 댓글의 대댓글들을 찾아서 삭제
        Set<ArticleComment> childComments = articleComment.getChildComments();
        if (childComments != null && !childComments.isEmpty()) {
            // 대댓글 삭제
            articleCommentRepository.deleteAll(childComments);
        }

        // 댓글 삭제
        articleCommentRepository.delete(articleComment);
    }

    // 대댓글 추가 메서드
    @Transactional(readOnly = false)
    public void saveReply(Long articleId, Long parentCommentId, ArticleCommentRequest request, String username) {
        // 부모 댓글이 있는지 확인
        ArticleComment parentComment = articleCommentRepository.findById(parentCommentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 부모 댓글이 존재하지 않습니다. id: " + parentCommentId));

        // 댓글 작성자 정보 등록
        Member member = memberRepository.findByEmail(username);
        request.setMember(member);

        // 대댓글 생성
        ArticleComment reply = request.toEntity();
        reply.setArticle(parentComment.getArticle());

        // 부모 댓글에 대댓글 추가
        parentComment.addChildComment(reply);
        articleCommentRepository.save(reply);
    }


    @Transactional(readOnly = true)
    public List<ArticleComment> findReplies(Long articleCommentId, Long parentCommentId) {
        ArticleComment articleComment = articleCommentRepository.findById(articleCommentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id: " + articleCommentId));

        return new ArrayList<>(articleComment.getChildComments());
    }
    @Transactional
    public void deleteReply(Long replyId) {
        articleCommentRepository.deleteById(replyId);
    }


}
