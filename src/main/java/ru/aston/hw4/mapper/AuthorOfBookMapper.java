package ru.aston.hw4.mapper;

import ru.aston.hw4.dto.*;
import ru.aston.hw4.entity.Author;
import ru.aston.hw4.entity.Book;
import ru.aston.hw4.entity.IntellectualWork;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorOfBookMapper {

    public static AuthorOfBooksResponseDto toDto(Author entity) {
        AuthorOfBooksResponseDto authorOfBooksResponseDto = new AuthorOfBooksResponseDto();
        authorOfBooksResponseDto.setId(entity.getId());
        authorOfBooksResponseDto.setName(entity.getName());
        List<BookOfAuthorDto> authorDtos = new ArrayList<>();

        authorOfBooksResponseDto.setBooks(authorDtos);
        return authorOfBooksResponseDto;
    }

    public static Author toEntity(AuthorOfBooksCreationDto dto) {
        Author author = new Author();
        author.setName(dto.getName());
        List<IntellectualWork> books = dto.getBooks().stream().map(AuthorOfBookMapper::toEntity).collect(Collectors.toList());
        author.setWorks(books);
        return author;
    }

    public static BookOfAuthorDto toDto(Book entity) {
        BookOfAuthorDto bookOfAuthorDto = new BookOfAuthorDto();
        bookOfAuthorDto.setName(entity.getName());
        bookOfAuthorDto.setGenre(entity.getGenre());
        bookOfAuthorDto.setYear(entity.getYear());
        return bookOfAuthorDto;
    }

    public static Book toEntity(BookOfAuthorDto dto) {
        Book book = new Book();
        if (dto.getId() != null) {
            book.setId(dto.getId());
        }
        book.setName(dto.getName());
        book.setGenre(dto.getGenre());
        book.setYear(dto.getYear());
        return book;
    }
}
