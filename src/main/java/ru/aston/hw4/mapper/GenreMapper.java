package ru.aston.hw4.mapper;

import ru.aston.hw4.dto.GenreDto;
import ru.aston.hw4.entity.Genre;

public class GenreMapper {

    public static GenreDto toDto(Genre entity) {
        GenreDto genreDto = new GenreDto();
        genreDto.setId(entity.getId());
        genreDto.setName(entity.getName());
        return genreDto;
    }

    public static Genre toEntity(GenreDto dto) {
        Genre genre = new Genre();
        genre.setName(dto.getName());
        if (dto.getId() != null) {
            genre.setId(dto.getId());
        }
        return genre;
    }
}
