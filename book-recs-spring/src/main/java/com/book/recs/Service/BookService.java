package com.book.recs.Service;

import com.book.recs.Entity.Book;
import com.book.recs.GoogleBooksResponse;
import com.book.recs.Repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final RestTemplate restTemplate;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        this.restTemplate = new RestTemplate();
    }

    public List<Book> searchBooks(String query) {
        String apiUrl = "https://www.googleapis.com/books/v1/volumes?q=" + query;

        try {
            GoogleBooksResponse response = restTemplate.getForObject(apiUrl, GoogleBooksResponse.class);

            if (response != null && response.getItems() != null) {
                return response.getItems().stream()
                        .map(item -> mapGoogleBookToEntity(item))
                        .collect(Collectors.toList());
            }
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to fetch books from Google Books API: " + e.getMessage());
        }

        return List.of();
    }

    public Book getBookByIsbn(String isbn) {
        Optional<Book> bookOptional = Optional.ofNullable(bookRepository.findByIsbn(isbn));
        if (bookOptional.isPresent()) {
            return bookOptional.get();
        }

        String apiUrl = "https://www.googleapis.com/books/v1/volumes/" + isbn;

        try {
            GoogleBooksResponse.Item item = restTemplate.getForObject(apiUrl, GoogleBooksResponse.Item.class);

            if (item != null) {
                Book book = mapGoogleBookToEntity(item);
                return bookRepository.save(book);
            }
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to fetch book from Google Books API: " + e.getMessage());
        }

        throw new RuntimeException("Book not found with ISBN: " + isbn);
    }


    private Book mapGoogleBookToEntity(GoogleBooksResponse.Item item) {
        Book book = new Book();
        book.setIsbn(item.getId());
        book.setTitle(item.getVolumeInfo().getTitle());

        if (item.getVolumeInfo().getAuthors() != null) {
            book.setAuthor(String.join(", ", item.getVolumeInfo().getAuthors()));
        } else {
            book.setAuthor("Unknown Author");
        }

        return book;
    }
}
