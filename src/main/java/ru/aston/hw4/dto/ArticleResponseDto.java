package ru.aston.hw4.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ArticleResponseDto {
    private Integer id;

    private String name;

    private String journal;

    private Integer year;

    private Integer citationCount;

    private List<AuthorOfWorkDto> authors = new ArrayList<>();
}
