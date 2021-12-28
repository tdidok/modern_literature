package com.tdidok.literature.booksservice.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookDto {
    String title;
    Long author;
    String type;
    String description;
    String genre;
    String size;
    int price;
}
