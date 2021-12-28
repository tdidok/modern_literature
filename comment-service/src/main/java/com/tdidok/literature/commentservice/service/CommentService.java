package com.tdidok.literature.commentservice.service;

import com.tdidok.literature.commentservice.repository.CommentRepository;
import com.tdidok.literature.commentservice.repository.model.Comment;
import com.tdidok.literature.commentservice.repository.model.CommentType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public List<Comment> getAllByType (String typeString){
        CommentType type = CommentType.valueOf(typeString);
        return commentRepository.findAllByTypeIs(type);
    }

    public Comment getById (Long id) throws IllegalArgumentException {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isEmpty()) throw new IllegalArgumentException("Comment not found");
        return comment.get();
    }

    public Long create (Long sender, Long book, Long receiver,
                        CommentType type, String text) throws IllegalArgumentException {
        Comment comment = new Comment(sender, book, receiver, type, text);
        Comment savedComment = commentRepository.save(comment);
        return savedComment.getId();
    }

    public void update (Long id, String text){
        Comment comment = getById(id);
        if (text != null && !text.isBlank()) comment.setText(text);
        commentRepository.save(comment);
    }

    public void delete (Long id){
        commentRepository.deleteById(id);
    }
}
