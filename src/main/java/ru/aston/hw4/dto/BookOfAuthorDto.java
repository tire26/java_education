package ru.aston.hw4.dto;

import lombok.Data;
import ru.aston.hw4.entity.Genre;

/**
 * DTO {@link ru.aston.hw4.entity.Book} для DTO авторов
 */
@Data
public class BookOfAuthorDto {

    private Integer id;

    private String name;

    private Integer year;

    private Genre genre;
}
