package com.book.recs.Service;

import com.book.recs.Entity.Book;
import com.book.recs.Entity.Review;
import com.book.recs.Entity.User;
import com.book.recs.Repository.BookRepository;
import com.book.recs.Repository.ReviewRepository;
import com.book.recs.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookService bookService;

    public ReviewService(ReviewRepository reviewRepository, BookRepository bookRepository, UserRepository userRepository, BookService bookService) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.bookService = bookService;
    }

    public Review saveReview(String username, String isbn, Review review) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Book book = bookRepository.findByIsbn(isbn);
        if (book == null) {
            book = bookService.getBookByIsbn(isbn);
            if (book == null) {
                throw new RuntimeException("Failed to fetch book with ISBN: " + isbn);
            }
        }

        review.setUser(user);
        review.setBook(book);
        return reviewRepository.save(review);
    }

    public List<Review> getReviewsByBook(String isbn) {
        Book book = bookRepository.findByIsbn(isbn);

        if (book == null) {
            book = bookService.getBookByIsbn(isbn);
            if (book == null) {
                throw new RuntimeException("Book not found with ISBN: " + isbn);
            }
        }

        List<Review> reviews = book.getReviews();
        if (reviews == null || reviews.isEmpty()) {
            System.out.println("No reviews found for book with ISBN: " + isbn);
            return List.of();
        }

        return reviews;
    }
}
