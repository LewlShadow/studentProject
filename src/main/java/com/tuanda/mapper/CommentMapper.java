package com.tuanda.mapper;

import com.tuanda.dto.request.PostCommentDTO;
import com.tuanda.dto.response.ResponseCommentDTO;
import com.tuanda.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.sql.Timestamp;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    ResponseCommentDTO mapToResponseCommentDTO(Comment comment);

    @Mapping(source = "postCommentDTO.parentLevel", target = "level", qualifiedByName = "getCurrentLevel")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "username", target = "username")
    Comment mapToComment(PostCommentDTO postCommentDTO, String username, Timestamp date);

    @Named("getCurrentLevel")
    default int getCurrentLevel(Integer parentLevel) {
        if (parentLevel == null) return 0;
        return parentLevel + 1;
    }
}
