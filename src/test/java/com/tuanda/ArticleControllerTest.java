package com.tuanda;


import com.tuanda.controller.ArticleController;
import com.tuanda.dto.request.PostArticleDTO;
import com.tuanda.dto.request.PostCommentDTO;
import com.tuanda.dto.response.DetailArticleDTO;
import com.tuanda.dto.response.ResponseArticleDTO;
import com.tuanda.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ArticleController.class)
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithUserDetails("USEER")
    @WithMockUser(roles = "USEER")
    public void testGetArticles() throws Exception {
        List<ResponseArticleDTO> responseArticleDTOS = new ArrayList<>();
        Page<ResponseArticleDTO> page = new PageImpl<>(responseArticleDTOS);
        when(articleService.getArticle(any())).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/article/get-articles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    public void testViewArticle() throws Exception {
        DetailArticleDTO detailArticleDTO = new DetailArticleDTO();
        when(articleService.viewArticle(any(Long.class))).thenReturn(detailArticleDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/article/view/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @WithMockUser(authorities = "USER")
    public void testPostComment() throws Exception {
        PostCommentDTO postCommentDTO = new PostCommentDTO();
        when(articleService.comment(any(PostCommentDTO.class), any(String.class))).thenReturn(1L);

        mockMvc.perform(MockMvcRequestBuilders.post("/article/post-comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"comment\":\"Test comment\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    public void testPostArticle() throws Exception {
        PostArticleDTO postArticleDTO = new PostArticleDTO();
        when(articleService.postArticle(any(PostArticleDTO.class), any(String.class))).thenReturn(1L);

        mockMvc.perform(MockMvcRequestBuilders.post("/article/post-article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test title\", \"content\":\"Test content\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    public void testGetAllDetailArticles() throws Exception {
        List<DetailArticleDTO> dtos = new ArrayList<>();
        Page<DetailArticleDTO> articleDTOS = new PageImpl<>(dtos);
        when(articleService.getAllDetails(any())).thenReturn(articleDTOS);

        mockMvc.perform(MockMvcRequestBuilders.get("/article/get-all-details")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
}
