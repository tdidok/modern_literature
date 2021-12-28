package com.tdidok.literature.booksservice.api;

import com.tdidok.literature.booksservice.api.dto.BookDto;
import com.tdidok.literature.booksservice.repository.model.Book;
import com.tdidok.literature.booksservice.repository.model.BookSize;
import com.tdidok.literature.booksservice.repository.model.BookType;
import com.tdidok.literature.booksservice.service.BookService;
import org.json.simple.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/books")
public class BookController {
    private final BookService bookService;
    private final String userService = "http://identity-service:8081";
    public BookController(BookService bookService) {this.bookService = bookService;}


    @GetMapping
    public ResponseEntity<List<Book>> getAll(){
        return ResponseEntity.ok(bookService.getAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(bookService.getById(id));
        }catch (IllegalArgumentException ex){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "author/{id}")
    public ResponseEntity<List<Book>> getByAuthor (@PathVariable Long id) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<JSONObject> response = restTemplate.exchange(userService + "/users/" +
                    id, HttpMethod.GET, null, JSONObject.class);
            return ResponseEntity.ok(bookService.getByAuthor(id));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody BookDto bookDto){
        try{
            RestTemplate restTemplate = new RestTemplate();
            String title = bookDto.getTitle();
            BookSize bookSize = BookSize.valueOf(bookDto.getSize());
            BookType bookType = BookType.valueOf(bookDto.getType());
            String description = bookDto.getDescription();
            String genre = bookDto.getGenre();
            int price = bookDto.getPrice();
            Long author = bookDto.getAuthor();
            ResponseEntity<JSONObject> response = restTemplate.exchange(userService + "/users/" +
                    author, HttpMethod.GET, null, JSONObject.class);
            Long id = bookService.create(title, author, bookType, description, genre, bookSize, price);
            return ResponseEntity.created(URI.create("/books"+ id)).build();
        } catch (Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody BookDto bookDto){
        try{
            String title = bookDto.getTitle();
            BookType bookType = BookType.valueOf(bookDto.getType());
            String description = bookDto.getDescription();
            String genre = bookDto.getGenre();
            BookSize bookSize = BookSize.valueOf(bookDto.getSize());
            int price = bookDto.getPrice();
            bookService.update(id, title, bookType, description, genre, bookSize, price);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException ex){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try {
            bookService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }
}
