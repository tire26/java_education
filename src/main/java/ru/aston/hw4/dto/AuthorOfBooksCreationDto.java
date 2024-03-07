package ru.aston.hw4.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AuthorOfBooksCreationDto {

    private String name;

    private List<BookOfAuthorDto> books = new ArrayList<>();
}
