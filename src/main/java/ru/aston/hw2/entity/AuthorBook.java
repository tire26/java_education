package ru.aston.hw2.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthorBook {

    private Integer id;
    private Author author;
    private Book book;
}
