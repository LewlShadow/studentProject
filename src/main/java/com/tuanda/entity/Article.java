package com.tuanda.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String author;

    private String title;

    private Timestamp date;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Transient
    private String shortContent;
}
