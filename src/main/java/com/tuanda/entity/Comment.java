package com.tuanda.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "comment")
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "article_id", nullable = false)
    private Long articleId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "date", nullable = false)
    private Timestamp date;

    @Column(name = "level", nullable = false)
    private Integer level;

    @Column(name = "parent_comment")
    private Long parentCommentId;

    @Transient
    private Comment parentComment;

}