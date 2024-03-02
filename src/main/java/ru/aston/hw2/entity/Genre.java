package ru.aston.hw2.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Genre implements Id {
    private Integer id;
    private String name;
}
