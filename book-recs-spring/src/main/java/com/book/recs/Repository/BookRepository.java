package com.book.recs.Repository;

import com.book.recs.Entity.Book;
import com.book.recs.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByIsbn(String isbn);
    boolean existsByIsbn(String isbn);
}

