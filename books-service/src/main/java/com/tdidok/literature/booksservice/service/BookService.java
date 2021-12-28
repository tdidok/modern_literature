package com.tdidok.literature.booksservice.service;

import com.tdidok.literature.booksservice.repository.BookRepository;
import com.tdidok.literature.booksservice.repository.model.Book;
import com.tdidok.literature.booksservice.repository.model.BookSize;
import com.tdidok.literature.booksservice.repository.model.BookType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public final class BookService {
    private final BookRepository bookRepository;

    public List<Book> getAll (){
        return bookRepository.findAll();
    }

    public Book getById (Long id) throws  IllegalArgumentException {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) throw new IllegalArgumentException("No book with such id found");
        return book.get();
    }

    public List<Book> getByAuthor(Long id) {return bookRepository.findByAuthorIs(id);}

    public Long create(String title, Long author, BookType bookType, String description, String genre,
                       BookSize bookSize, int price) throws  IllegalArgumentException {
        Book book = new Book(title, author, bookType, description, genre, bookSize, price);
        Book savedBook = bookRepository.save(book);
        return savedBook.getId();
    }

    public void update (Long id, String title, BookType bookType, String description, String genre,
                        BookSize bookSize, int price) throws  IllegalArgumentException {
        Book book = getById(id);
        if (title != null && !title.isBlank()) book.setTitle(title);
        if (bookType != null) book.setType(bookType);
        if (description != null && !description.isBlank()) book.setDescription(description);
        if (genre != null && !genre.isBlank()) book.setGenre(genre);
        if (bookSize != null) book.setSize(bookSize);
        book.setPrice(price);
        bookRepository.save(book);

    }

    public void delete (Long id) {
        bookRepository.deleteById(id);
    }
}
