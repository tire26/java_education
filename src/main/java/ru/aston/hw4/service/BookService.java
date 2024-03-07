package ru.aston.hw4.service;

import ru.aston.hw4.dao.BookDao;
import ru.aston.hw4.dao.Dao;
import ru.aston.hw4.dto.BookCreationDto;
import ru.aston.hw4.dto.BookCreationWithIdDto;
import ru.aston.hw4.dto.BookResponseDto;
import ru.aston.hw4.entity.Author;
import ru.aston.hw4.entity.Book;
import ru.aston.hw4.mapper.BookMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class BookService {

    private final Dao<Book> bookDao = new BookDao();

    public List<BookResponseDto> getAll() {
        List<BookResponseDto> bookResponseDtos = new ArrayList<>();
        List<Book> books = bookDao.getAll();
        for (Book book : books) {
            BookResponseDto bookResponseDto = BookMapper.toDto(book);
            bookResponseDtos.add(bookResponseDto);
        }
        return bookResponseDtos;
    }

    public Optional<BookResponseDto> get(long bookId) {
        Optional<Book> optionalBook = bookDao.get(bookId);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            BookResponseDto dto = BookMapper.toDto(book);
            return Optional.of(dto);
        } else {
            return Optional.empty();
        }
    }

    public void save(BookCreationDto book) {
        Book entity = BookMapper.toEntity(book);
        for (Author author : entity.getAuthors()) {
            author.addWork(entity);
        }
        bookDao.save(entity);
    }

    public void update(BookCreationWithIdDto book) {
        Book entity = BookMapper.toEntity(book);
        entity.setId(book.getId());
        for (Author author : entity.getAuthors()) {
            author.addWork(entity);
        }
        bookDao.update(entity);
    }

    public void delete(long id) {
        bookDao.delete(id);
    }
}
