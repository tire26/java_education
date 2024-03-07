package ru.aston.hw4.mapper;

import ru.aston.hw4.dto.ArticleCreationDto;
import ru.aston.hw4.dto.ArticleResponseDto;
import ru.aston.hw4.entity.Article;

import java.util.stream.Collectors;

public class ArticleMapper {
    public static ArticleResponseDto toDto(Article article) {
        ArticleResponseDto articleResponseDto = new ArticleResponseDto();
        articleResponseDto.setId(article.getId());
        articleResponseDto.setName(article.getName());
        articleResponseDto.setYear(article.getYear());
        articleResponseDto.setJournal(article.getScientificJournal());
        articleResponseDto.setCitationCount(article.getCitationCount());
        articleResponseDto.setAuthors(article.getAuthors().stream().map(AuthorOfWorkMapper::toDto).collect(Collectors.toList()));
        return articleResponseDto;
    }

    public static Article toEntity(ArticleCreationDto articleCreationDto) {
        Article article = new Article();
        article.setName(articleCreationDto.getName());
        article.setYear(articleCreationDto.getYear());
        article.setScientificJournal(articleCreationDto.getJournal());
        article.setCitationCount(articleCreationDto.getCitationCount());
        article.setAuthors(articleCreationDto.getAuthors().stream().map(AuthorOfWorkMapper::toEntity).collect(Collectors.toList()));
        return article;
    }
}
