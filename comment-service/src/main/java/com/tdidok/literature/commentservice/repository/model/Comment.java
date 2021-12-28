package com.tdidok.literature.commentservice.repository.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "comments")
public final class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private Long sender;
    private Long book;
    private Long receiver;

    @Column(columnDefinition = "ENUM('Message', 'Review')")
    @Enumerated(EnumType.STRING)
    private CommentType type;
    @NotBlank
    private String text;

    public Comment(){}

    public Comment(Long sender, Long book, Long receiver, CommentType type, String text){
        this.sender = sender;
        this.book = book;
        this.receiver = receiver;
        this.type = type;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public Long getBook() {
        return book;
    }

    public void setBook(Long book) {
        this.book = book;
    }

    public Long getReceiver() {
        return receiver;
    }

    public void setReceiver(Long receiver) {
        this.receiver = receiver;
    }

    public CommentType getType() {
        return type;
    }

    public void setType(CommentType type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
