package ru.aston.hw4.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GenreDto {

    @JsonProperty("genre_id")
    private Integer id;

    @JsonProperty("genre_name")
    private String name;
}
