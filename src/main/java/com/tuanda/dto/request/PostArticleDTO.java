package com.tuanda.dto.request;

import jakarta.persistence.Column;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class PostArticleDTO {
    private String content;
    private String title;
}
