package com.tdidok.literature.commentservice.api;

import com.tdidok.literature.commentservice.api.dto.CommentDto;
import com.tdidok.literature.commentservice.repository.model.Comment;
import com.tdidok.literature.commentservice.repository.model.CommentType;
import com.tdidok.literature.commentservice.service.CommentService;
import org.json.simple.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "comments")
public class CommentController {
    private final CommentService commentService;
    private final String userService = "http://identity-service:8081";
    private final String bookService = "http://books-service:8082";

    public CommentController(CommentService commentService){ this.commentService = commentService;}

    @GetMapping(value = "/messages")
    public ResponseEntity<List<Comment>> getAllMessages () {
        return ResponseEntity.ok(commentService.getAllByType("Message"));
    }

    @GetMapping(value = "/reviews")
    public ResponseEntity<List<Comment>> getAllReviews() {
        return ResponseEntity.ok(commentService.getAllByType("Review"));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Comment> getMessageById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(commentService.getById(id));
        }catch (IllegalArgumentException ex){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CommentDto commentDto){
        try{
            RestTemplate restTemplate = new RestTemplate();
            Long sender = commentDto.getSender();
            Long book = commentDto.getBook();
            Long receiver = commentDto.getReceiver();
            CommentType type = CommentType.valueOf(commentDto.getType());
            String text = commentDto.getText();
            if (sender != 0) {
                ResponseEntity<JSONObject> response = restTemplate.exchange(userService + "/users/" +
                        sender, HttpMethod.GET, null, JSONObject.class);
            }
            if (receiver != 0) {
                ResponseEntity<JSONObject> response = restTemplate.exchange(userService + "/users/" +
                        receiver, HttpMethod.GET, null, JSONObject.class);
            }
            if (book != 0) {
                ResponseEntity<JSONObject> response = restTemplate.exchange(bookService + "/books/" +
                        book, HttpMethod.GET, null, JSONObject.class);
            }
            Long id = commentService.create(sender, book, receiver, type, text);
            return ResponseEntity.created(URI.create("/books"+ id)).build();
        } catch (Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody String text){
        try{
            commentService.update(id, text);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException ex){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try {
            commentService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }
}
