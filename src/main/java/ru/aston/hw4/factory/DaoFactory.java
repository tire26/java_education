package ru.aston.hw4.factory;

import ru.aston.hw4.dao.AuthorDao;
import ru.aston.hw4.dao.BookDao;
import ru.aston.hw4.dao.Dao;
import ru.aston.hw4.dao.GenreDao;
import ru.aston.hw4.entity.Author;
import ru.aston.hw4.entity.Book;
import ru.aston.hw4.entity.Genre;

public class DaoFactory {
    private static final DaoFactory INSTANCE = new DaoFactory();

    private final Dao<Book> bookDao = new BookDao();
    private final Dao<Author> authorDao = new AuthorDao();
    private final Dao<Genre> genreDao = new GenreDao();

    private DaoFactory() {
    }

    public static DaoFactory getInstance() {
        return INSTANCE;
    }

    public Dao<Book> getBookDao() {
        return bookDao;
    }

    public Dao<Author> getAuthorDao() {
        return authorDao;
    }

    public Dao<Genre> getGenreDao() {
        return genreDao;
    }
}
