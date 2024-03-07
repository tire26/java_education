package ru.aston.hw4.entity;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table
public class Author implements ru.aston.hw4.entity.Id {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
    })
    @JoinTable(name = "author_work",
            joinColumns = @JoinColumn(name = "id_author"),
            inverseJoinColumns = @JoinColumn(name = "id_work")
    )
    private List<IntellectualWork> works;

    public Author() {
        works = new ArrayList<>();
    }

    public Author(Integer id, String name) {
        this.setId(id);
        this.name = name;
        works = new ArrayList<>();
    }

    public void addWork(IntellectualWork intellectualWork) {
        if (!works.contains(intellectualWork)) {
            works.add(intellectualWork);
        }
        if (!intellectualWork.getAuthors().contains(this)) {
            intellectualWork.getAuthors().add(this);
        }
    }

    public void removeWork(IntellectualWork book) {
        works.remove(book);
        book.getAuthors().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        return Objects.equals(getId(), author.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
