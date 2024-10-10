package com.tuanda.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseArticleDTO {
    private long id;
    private String author;
    private String title;
    private Timestamp date;
    private String shortContent;
}
