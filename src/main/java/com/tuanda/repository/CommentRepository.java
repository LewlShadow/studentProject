package com.tuanda.repository;

import com.tuanda.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findCommentByArticleIdOrderByLevelAsc(long articleId);
}
