package com.tuanda.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailArticleDTO {
    private long id;
    private String author;
    private String title;
    private Timestamp date;
    private String content;
    private List<ResponseCommentDTO> responseCommentDTOS;
}
