package com.tuanda.dto.response;

import lombok.Data;
import java.sql.Timestamp;
import java.util.List;

@Data
public class DetailArticleDTO {
    private long id;
    private String author;
    private String title;
    private Timestamp date;
    private String content;
    private List<ResponseCommentDTO> responseCommentDTOS;
}
