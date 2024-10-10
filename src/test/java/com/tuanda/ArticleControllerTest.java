package com.tuanda;


import com.tuanda.controller.ArticleController;
import com.tuanda.dto.request.PostArticleDTO;
import com.tuanda.dto.request.PostCommentDTO;
import com.tuanda.dto.response.DetailArticleDTO;
import com.tuanda.dto.response.ResponseArticleDTO;
import com.tuanda.entity.Article;
import com.tuanda.repository.ArticleRepository;
import com.tuanda.service.ArticleService;
import com.tuanda.service.ArticleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ArticleControllerTest {

    @Mock
    private ArticleService articleService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private ArticleController controller;

    @Test
    public void testGetArticles() {
        List<ResponseArticleDTO> responseArticleDTOS = new ArrayList<>();
        responseArticleDTOS.add(new ResponseArticleDTO(1, "tuan", "title", new Timestamp(System.currentTimeMillis()), "summary"));
        when(articleService.getArticle(any())).thenReturn(new PageImpl<>(responseArticleDTOS));

        ResponseEntity<?> response = controller.getArticles(Pageable.unpaged());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        Page<ResponseArticleDTO> responseBody = (Page<ResponseArticleDTO>) ((Map) response.getBody()).get("Data");
        assertEquals(responseBody.getContent().get(0).getAuthor(), "tuan");
    }

    @Test
    public void testViewArticle() {
        DetailArticleDTO detailArticleDTO = new DetailArticleDTO();
        detailArticleDTO.setId(1);
        detailArticleDTO.setAuthor("tuan");
        when(articleService.viewArticle(any(Long.class))).thenReturn(detailArticleDTO);

        ResponseEntity<?> response = controller.viewArticle(1L);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        DetailArticleDTO responseBody = (DetailArticleDTO) ((Map) response.getBody()).get("Data");
        assertEquals(responseBody.getAuthor(), "tuan");
    }

    @Test
    public void testPostComment() {
        PostCommentDTO postCommentDTO = new PostCommentDTO();
        postCommentDTO.setArticleId(1L);
        postCommentDTO.setText("content test");
        when(articleService.comment(any(PostCommentDTO.class), any(String.class))).thenReturn(1L);
        when(authentication.getName()).thenReturn("tuan");

        ResponseEntity<?> response = controller.postComment(authentication, postCommentDTO);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        long responseBody = (long) ((Map) response.getBody()).get("Data");
        assertEquals(responseBody, 1);
    }

    @Test
    public void testPostArticle() throws Exception {
        PostArticleDTO postArticleDTO = new PostArticleDTO();
        postArticleDTO.setTitle("content title");
        when(articleService.postArticle(any(PostArticleDTO.class), any(String.class))).thenReturn(1L);
        when(authentication.getName()).thenReturn("tuan");

        ResponseEntity<?> response = controller.postArticle(authentication, postArticleDTO);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        long responseBody = (long) ((Map) response.getBody()).get("Data");
        assertEquals(responseBody, 1);
    }

    @Test
    public void testGetAllDetailArticles() throws Exception {
        List<DetailArticleDTO> dtos = new ArrayList<>();
        dtos.add(new DetailArticleDTO(1, "tuan", "title", new Timestamp(System.currentTimeMillis()), "content", new ArrayList<>()));
        Page<DetailArticleDTO> articleDTOS = new PageImpl<>(dtos);
        when(articleService.getAllDetails(any())).thenReturn(articleDTOS);

        ResponseEntity<?> response = controller.getAllDetailArticles(Pageable.unpaged());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        Page<DetailArticleDTO> responseBody = (Page<DetailArticleDTO>) ((Map) response.getBody()).get("Data");
        assertEquals(responseBody.get().findFirst().get().getAuthor(), "tuan");
    }

}
