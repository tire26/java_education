package ru.aston.hw4.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Entity(name = "Book")
@Table(name = "books")
public class Book implements Id {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    private String name;

    @Setter
    private Integer year;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private Genre genre;

    @ManyToMany(mappedBy = "books")
    private List<Author> authors;

    public Book() {
    }

    public Book(Integer id, String name, Integer year, Genre genre) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.genre = genre;
    }


    public void addAll(List<Author> authors) {
        if (authors != null) {
            this.authors.addAll(authors);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
