package com.tuanda.service;

import com.tuanda.dto.request.PostArticleDTO;
import com.tuanda.dto.request.PostCommentDTO;
import com.tuanda.dto.response.ResponseArticleDTO;
import com.tuanda.dto.response.DetailArticleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleService {
    Page<ResponseArticleDTO> getArticle(Pageable pageable);

    DetailArticleDTO viewArticle(long articleId);

    Long comment(PostCommentDTO dto, String username);

    Long postArticle(PostArticleDTO postArticleDTO, String username);

    Page<DetailArticleDTO> getAllDetails(Pageable pageable);
}
