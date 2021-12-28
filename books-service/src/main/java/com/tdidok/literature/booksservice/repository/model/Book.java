package com.tdidok.literature.booksservice.repository.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "books")
public final class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotNull
    private Long author;

    @Column(columnDefinition = "ENUM('Original', 'Translation')")
    @Enumerated(EnumType.STRING)
    @NotNull
    private BookType type;


    @NotBlank
    private String description;

    @NotBlank
    private String genre;

    @Column(columnDefinition = "(ENUM('Short', 'Medium', 'Long')")
    @Enumerated(EnumType.STRING)
    @NotNull
    private BookSize size;


    @NotNull
    private int price;

    public Book(){}

    public Book(String title, Long author, BookType type, String description,
                String genre, BookSize size, int price){
        this.title = title;
        this.author = author;
        this.type = type;
        this.description = description;
        this.genre = genre;
        this.size = size;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BookType getType() {
        return type;
    }

    public void setType(BookType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public BookSize getSize() {
        return size;
    }

    public void setSize(BookSize size) {
        this.size = size;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Long getAuthor() {
        return author;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }

}
