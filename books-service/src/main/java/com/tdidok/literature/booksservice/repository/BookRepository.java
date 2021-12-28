package com.tdidok.literature.booksservice.repository;

import com.tdidok.literature.booksservice.repository.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthorIs(Long id);
}
