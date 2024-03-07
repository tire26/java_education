package ru.aston.hw4.service;

import ru.aston.hw4.dao.ArticleDao;
import ru.aston.hw4.dao.Dao;
import ru.aston.hw4.dto.ArticleCreationDto;
import ru.aston.hw4.dto.ArticleCreationWithIdDto;
import ru.aston.hw4.dto.ArticleResponseDto;
import ru.aston.hw4.entity.Article;
import ru.aston.hw4.entity.Author;
import ru.aston.hw4.mapper.ArticleMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArticleService {
    private final Dao<Article> articleDao = new ArticleDao();

    public List<ArticleResponseDto> getAll() {
        List<ArticleResponseDto> articleResponseDtos = new ArrayList<>();
        List<Article> articles = articleDao.getAll();
        for (Article article : articles) {
            ArticleResponseDto articleResponseDto = ArticleMapper.toDto(article);
            articleResponseDtos.add(articleResponseDto);
        }
        return articleResponseDtos;
    }

    public Optional<ArticleResponseDto> get(long articleId) {
        Optional<Article> optionalArticle = articleDao.get(articleId);
        if (optionalArticle.isPresent()) {
            Article article = optionalArticle.get();
            ArticleResponseDto dto = ArticleMapper.toDto(article);
            return Optional.of(dto);
        } else {
            return Optional.empty();
        }
    }

    public void save(ArticleCreationDto articleCreationDto) {
        Article entity = ArticleMapper.toEntity(articleCreationDto);
        for (Author author : entity.getAuthors()) {
            author.addWork(entity);
        }
        articleDao.save(entity);
    }

    public void update(ArticleCreationWithIdDto book) {
        Article entity = ArticleMapper.toEntity(book);
        entity.setId(book.getId());
        for (Author author : entity.getAuthors()) {
            author.addWork(entity);
        }
        articleDao.update(entity);
    }

    public void delete(long id) {
        articleDao.delete(id);
    }
}
