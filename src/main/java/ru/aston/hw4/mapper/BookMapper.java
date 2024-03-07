package ru.aston.hw4.mapper;

import ru.aston.hw4.dto.*;
import ru.aston.hw4.entity.Author;
import ru.aston.hw4.entity.Book;
import ru.aston.hw4.entity.Genre;

import java.util.List;
import java.util.stream.Collectors;

public class BookMapper {

    public static BookResponseDto toDto(Book entity) {
        BookResponseDto bookResponseDto = new BookResponseDto();
        bookResponseDto.setId(entity.getId());
        bookResponseDto.setName(entity.getName());
        bookResponseDto.setGenre(GenreMapper.toDto(entity.getGenre()));
        bookResponseDto.setYear(bookResponseDto.getYear());
        List<AuthorOfWorkDto> authorDtos = entity.getAuthors().stream().map(AuthorOfWorkMapper::toDto).collect(Collectors.toList());
        bookResponseDto.setAuthors(authorDtos);
        return bookResponseDto;
    }

    public static Book toEntity(BookCreationDto dto) {
        Book book = new Book();
        book.setName(dto.getName());
        book.setYear(dto.getYear());
        Genre genre = new Genre();
        genre.setId(dto.getGenre().getId());
        genre.setName(dto.getGenre().getName());
        genre.addBook(book);
        book.setGenre(genre);
        List<Author> authors = dto.getAuthors().stream().map(AuthorOfWorkMapper::toEntity).collect(Collectors.toList());
        book.setAuthors(authors);
        return book;
    }
}
