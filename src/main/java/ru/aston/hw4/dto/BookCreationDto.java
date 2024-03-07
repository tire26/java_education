package ru.aston.hw4.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BookCreationDto {

    private String name;

    private Integer year;

    private GenreDto genre;

    private List<AuthorOfWorkDto> authors = new ArrayList<>();

    public BookCreationDto() {
    }

    public BookCreationDto(String name, Integer year, GenreDto genre) {
        this.name = name;
        this.year = year;
        this.genre = genre;
    }
}
