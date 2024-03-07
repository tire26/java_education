package ru.aston.hw4.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO {@link ru.aston.hw4.entity.Author} для DTO книг
 */
@Data
@AllArgsConstructor
public class AuthorOfWorkDto {

    @JsonProperty("author_id")
    private Integer id;

    @JsonProperty("author_name")
    private String name;

    public AuthorOfWorkDto() {

    }
}
