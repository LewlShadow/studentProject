package com.tuanda.dto.request;

import lombok.Data;

@Data
public class PostCommentDTO {
    private Long articleId;
    private Long parentCommentId;
    private Integer parentLevel;
    private String text;
}
