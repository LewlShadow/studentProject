package com.tuanda.mapper;

import com.tuanda.dto.request.PostArticleDTO;
import com.tuanda.dto.response.DetailArticleDTO;
import com.tuanda.dto.response.ResponseArticleDTO;
import com.tuanda.entity.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.sql.Timestamp;

@Mapper(componentModel = "spring")
public interface ArticleMapper {


    @Mapping(source = "content", target = "shortContent", qualifiedByName = "shorten")
    ResponseArticleDTO mapToArticleDTO(Article article);

    @Named("shorten")
    default String shortenContent(String content){
        return content.substring(0, Math.min(30, content.length()));
    }

    DetailArticleDTO mapToArticleDTOWithDetail(Article article);

    @Mapping(source = "time", target = "date")
    @Mapping(source = "username", target = "author")
    Article mapToArticle(PostArticleDTO dto, String username, Timestamp time);
}
