package com.tdidok.literature.commentservice.repository;

import com.tdidok.literature.commentservice.repository.model.Comment;
import com.tdidok.literature.commentservice.repository.model.CommentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findAllByTypeIs(CommentType type);
}
