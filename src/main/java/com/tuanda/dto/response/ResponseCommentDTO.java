package com.tuanda.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseCommentDTO {
    private Long id;
    private String username;
    private String text;
    private Timestamp date;
    private List<ResponseCommentDTO> replies = new ArrayList<>();
    private Long level;
    private Long articleId;
    @JsonIgnore
    private Long parentCommentId;

    public boolean isRoot() {
        return level == 0;
    }

    ;
}
