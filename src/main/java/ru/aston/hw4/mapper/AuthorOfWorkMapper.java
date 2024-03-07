package ru.aston.hw4.mapper;

import ru.aston.hw4.dto.AuthorOfWorkDto;
import ru.aston.hw4.entity.Author;

public class AuthorOfWorkMapper {
    public static AuthorOfWorkDto toDto(Author entity) {
        AuthorOfWorkDto authorOfWorkDto = new AuthorOfWorkDto();
        authorOfWorkDto.setId(entity.getId());
        authorOfWorkDto.setName(entity.getName());
        return authorOfWorkDto;
    }

    public static Author toEntity(AuthorOfWorkDto dto) {
        Author author = new Author();
        author.setName(dto.getName());
        if (dto.getId() != null) {
            author.setId(dto.getId());
        }
        return author;
    }
}
