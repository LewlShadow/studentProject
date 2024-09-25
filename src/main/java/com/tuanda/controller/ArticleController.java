package com.tuanda.controller;

import com.tuanda.common.EntityResponse;
import com.tuanda.dto.request.PostArticleDTO;
import com.tuanda.dto.request.PostCommentDTO;
import com.tuanda.dto.request.UserRequestDTO;
import com.tuanda.service.ArticleService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController extends BaseController {

    @Autowired
    private ArticleService articleService;

    @SneakyThrows
    @GetMapping("/get-articles")
    public ResponseEntity<?> getArticles(Pageable pageable) {
        return EntityResponse.generateSuccessResponse(articleService.getArticle(pageable));
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<?> viewArticle(@PathVariable Long id) {
        return EntityResponse.generateSuccessResponse(articleService.viewArticle(id));
    }

    @PostMapping("/post-comment")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> postComment(Authentication authentication, @RequestBody PostCommentDTO postCommentDTO) {
        String username = authentication.getName();
        return EntityResponse.generateSuccessResponse(articleService.comment(postCommentDTO, username));
    }

    @PostMapping("/post-article")
    public ResponseEntity<?> postArticle(Authentication authentication, @RequestBody PostArticleDTO postArticleDTO) {
        String username = authentication.getName();
        return EntityResponse.generateSuccessResponse(articleService.postArticle(postArticleDTO, username));
    }

    @GetMapping("/get-all-details")
    public ResponseEntity<?> getAllDetailArticles(Pageable pageable) {
        return EntityResponse.generateSuccessResponse(articleService.getAllDetails(pageable));
    }
}
