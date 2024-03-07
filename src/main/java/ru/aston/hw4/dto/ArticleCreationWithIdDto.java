package ru.aston.hw4.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleCreationWithIdDto extends ArticleCreationDto {

    private Integer id;

    public ArticleCreationWithIdDto(Integer id, String name, Integer year, String journal, Integer citationCount) {
        super(name, year, journal, citationCount);
        this.id = id;
    }

    public ArticleCreationWithIdDto() {
    }
}
