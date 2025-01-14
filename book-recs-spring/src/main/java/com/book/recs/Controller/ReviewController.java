package com.book.recs.Controller;

import com.book.recs.Entity.Review;
import com.book.recs.Entity.User;
import com.book.recs.Service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@CrossOrigin(origins = "http://localhost:5173")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/{isbn}")
    public Review addReview(@RequestParam String username, @PathVariable String isbn, @RequestBody Review review) {

        return reviewService.saveReview(username, isbn, review);
    }

    @GetMapping("/book/{isbn}")
    public List<Review> getReviewsByBook(@PathVariable String isbn) {
        return reviewService.getReviewsByBook(isbn);
    }
}
