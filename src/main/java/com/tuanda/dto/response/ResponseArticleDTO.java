package com.tuanda.dto.response;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class ResponseArticleDTO {
    private long id;
    private String author;
    private String title;
    private Timestamp date;
    private String shortContent;
}
