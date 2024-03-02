package ru.aston.hw4.service;

import ru.aston.hw4.dao.BookDao;
import ru.aston.hw4.dao.Dao;
import ru.aston.hw4.entity.Book;

import java.util.List;
import java.util.Optional;

public class BookService {

    private final Dao<Book> bookDao = new BookDao();

    public List<Book> getAll() {
        return bookDao.getAll();
    }

    public Optional<Book> get(long bookId) {
        return bookDao.get(bookId);
    }

    public void save(Book book) {
        bookDao.save(book);
    }

    public void update(Book book) {
        bookDao.update(book);
    }

    public void delete(Book book) {
        bookDao.delete(book);
    }
}
