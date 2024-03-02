package ru.aston.hw2.entity;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Author implements Id {
    private Integer id;
    private String name;
    private List<Book> books;

    public Author() {
    }

    public Author(Integer id, String name) {
        this.id = id;
        this.name = name;
        books = new ArrayList<>();
    }

    public void addAll(List<Book> books) {
        if (books != null) {
            this.books.addAll(books);
        }
    }
}
