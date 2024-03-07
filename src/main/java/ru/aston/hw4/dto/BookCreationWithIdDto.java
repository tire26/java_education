package ru.aston.hw4.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BookCreationWithIdDto extends BookCreationDto {

    private Integer id;

    public BookCreationWithIdDto(Integer id, String name, Integer year, GenreDto genre) {
        super(name, year, genre);
        this.id = id;
    }

    public BookCreationWithIdDto() {
    }
}
