package ru.aston.hw2.entity;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Book implements Id {

    private Integer id;
    private String name;
    private Integer year;
    private Genre genre;
    private List<Author> authors;

    public Book(Integer id, String name, Integer year, Genre genre) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.genre = genre;
        authors = new ArrayList<>();
    }


    public void addAll(List<Author> authors) {
        if (authors != null) {
            this.authors.addAll(authors);
        }
    }
}
