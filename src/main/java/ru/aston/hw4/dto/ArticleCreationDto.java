package ru.aston.hw4.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ArticleCreationDto {

    private String name;

    private Integer year;

    private String journal;

    private Integer citationCount;

    private List<AuthorOfWorkDto> authors = new ArrayList<>();

    public ArticleCreationDto() {
    }

    public ArticleCreationDto(String name, Integer year, String journal, Integer citationCount) {
        this.name = name;
        this.year = year;
        this.journal = journal;
        this.citationCount = citationCount;
    }
}
