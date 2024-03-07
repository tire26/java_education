package ru.aston.hw4.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BookResponseDto {

    private Integer id;

    private String name;

    private Integer year;

    private GenreDto genre;

    private List<AuthorOfWorkDto> authors = new ArrayList<>();
}
