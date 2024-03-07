package ru.aston.hw4.dao;

import ru.aston.hw4.entity.Id;

import java.util.List;
import java.util.Optional;

public interface Dao<T extends Id> {
    Optional<T> get(long id);

    List<T> getAll();

    void save(T t);

    void update(T t);

    void delete(long t);
}
