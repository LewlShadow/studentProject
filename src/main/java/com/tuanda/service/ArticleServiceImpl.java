package com.tuanda.service;

import com.tuanda.dto.request.PostArticleDTO;
import com.tuanda.dto.request.PostCommentDTO;
import com.tuanda.dto.response.ResponseArticleDTO;
import com.tuanda.dto.response.ResponseCommentDTO;
import com.tuanda.dto.response.DetailArticleDTO;
import com.tuanda.entity.Article;
import com.tuanda.entity.Comment;
import com.tuanda.mapper.ArticleMapper;
import com.tuanda.mapper.CommentMapper;
import com.tuanda.repository.ArticleRepository;
import com.tuanda.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.*;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CommentMapper commentMapper;


    @Override
    public Page<ResponseArticleDTO> getArticle(Pageable pageable) {
        Page<Article> articles = articleRepository.findAll(pageable);
        return articles.map(article -> articleMapper.mapToArticleDTO(article));
    }

    @Override
    public DetailArticleDTO viewArticle(long articleId) {
        // build article
        Article article = this.articleRepository.findById(articleId).orElse(null);
        if (article == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found article");
        DetailArticleDTO detailArticleDTO = this.articleMapper.mapToArticleDTOWithDetail(article);
        // build comments
        List<Comment> comments = this.commentRepository.findCommentByArticleIdOrderByLevelAsc(articleId);
        if (comments != null && !comments.isEmpty()) {
            List<ResponseCommentDTO> responseCommentDTOInTree = buildTree(comments);
            detailArticleDTO.setResponseCommentDTOS(responseCommentDTOInTree);
        }
        return detailArticleDTO;
    }

    private List<ResponseCommentDTO> buildTree(List<Comment> comments) {
        List<ResponseCommentDTO> commentDTOs = comments.stream().map(commentMapper::mapToResponseCommentDTO).toList();
        List<ResponseCommentDTO> roots = new ArrayList<>();
        Map<Long, ResponseCommentDTO> searchMap = new HashMap<>();
        // build search map
        commentDTOs.forEach(c -> {
            searchMap.put(c.getId(), c);
            if (c.isRoot()) roots.add(c);
        });

        // build tree
        Queue<ResponseCommentDTO> queue = new LinkedList<>(commentDTOs);
        // build root list
        while (!queue.isEmpty()) {
            ResponseCommentDTO node = queue.poll();
            // Skip root
            if (node.getParentCommentId() == null) continue;
            ResponseCommentDTO parentNode = searchMap.get(node.getParentCommentId());
            parentNode.getReplies().add(node);
        }

        return roots;
    }

    @Override
    public Long comment(PostCommentDTO dto, String username) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Comment comment = this.commentMapper.mapToComment(dto, username, timestamp);
        Comment storeComment = this.commentRepository.save(comment);
        return storeComment.getId();
    }

    @Override
    public Long postArticle(PostArticleDTO postArticleDTO, String username) {
        validateContentArticle(postArticleDTO.getContent(), 3, 200);
        validateContentArticle(postArticleDTO.getTitle(), 1, 30);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Article article = this.articleMapper.mapToArticle(postArticleDTO, username, now);
        Article storeArticle = this.articleRepository.save(article);
        return storeArticle.getId();
    }

    @Override
    public Page<DetailArticleDTO> getAllDetails(Pageable pageable) {
        Page<Article> articles = articleRepository.findAll(pageable);
        return articles.map(article -> {
            DetailArticleDTO detailArticleDTO = this.articleMapper.mapToArticleDTOWithDetail(article);
            // build comments
            List<Comment> comments = this.commentRepository.findCommentByArticleIdOrderByLevelAsc(article.getId());
            if (comments != null && !comments.isEmpty()) {
                List<ResponseCommentDTO> responseCommentDTOInTree = buildTree(comments);
                detailArticleDTO.setResponseCommentDTOS(responseCommentDTOInTree);
            }
            return detailArticleDTO;
        });
    }

    private void validateContentArticle(String content, int limit, int max) {
        if (content == null || content.isBlank() || content.length() < limit || content.length() > max)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Content");
    }

}
