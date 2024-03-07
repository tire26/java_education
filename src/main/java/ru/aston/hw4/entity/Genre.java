package ru.aston.hw4.entity;

import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
@Setter
@Table
public class Genre implements ru.aston.hw4.entity.Id {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(
            mappedBy = "genre",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Book> books = new ArrayList<>();

    public Genre(Integer id, String name) {
        this.setId(id);
        this.name = name;
    }

    public Genre() {
    }

    public void addBook(Book b) {
        books.add(b);
        b.setGenre(this);
    }

    public void removeBook(Book b) {
        books.remove(b);
        b.setGenre(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return Objects.equals(getId(), genre.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
