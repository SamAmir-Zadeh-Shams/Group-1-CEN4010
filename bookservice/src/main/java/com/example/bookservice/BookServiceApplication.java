
package com.example.bookservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@RestController
@RequestMapping("/api")
public class BookServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookServiceApplication.class, args);
    }

    static class Rating {
        private int rating;
        private String userId;
        private String bookId;
        private LocalDateTime dateStamp;

        public Rating(int rating, String userId, String bookId) {
            this.rating = rating;
            this.userId = userId;
            this.bookId = bookId;
            this.dateStamp = LocalDateTime.now();
        }

        public int getRating() { return rating; }
        public String getUserId() { return userId; }
        public String getBookId() { return bookId; }
        public LocalDateTime getDateStamp() { return dateStamp; }
    }

    static class Comment {
        private String comment;
        private String userId;
        private String bookId;
        private LocalDateTime dateStamp;

        public Comment(String comment, String userId, String bookId) {
            this.comment = comment;
            this.userId = userId;
            this.bookId = bookId;
            this.dateStamp = LocalDateTime.now();
        }

        public String getComment() { return comment; }
        public String getUserId() { return userId; }
        public String getBookId() { return bookId; }
        public LocalDateTime getDateStamp() { return dateStamp; }
    }

    private final List<Rating> ratings = new ArrayList<>();
    private final List<Comment> comments = new ArrayList<>();

    @PostMapping("/ratings")
    public void createRating(
            @RequestParam int rating,
            @RequestParam String userId,
            @RequestParam String bookId) {

        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5 stars.");
        }

        ratings.add(new Rating(rating, userId, bookId));
    }

    @PostMapping("/comments")
    public void createComment(
            @RequestParam String comment,
            @RequestParam String userId,
            @RequestParam String bookId) {

        comments.add(new Comment(comment, userId, bookId));
    }

    @GetMapping("/comments")
    public List<Comment> getComments(@RequestParam String bookId) {

        return comments.stream()
                .filter(c -> c.getBookId().equalsIgnoreCase(bookId))
                .collect(Collectors.toList());
    }

    @GetMapping("/ratings/average")
    public double getAverageRating(@RequestParam String bookId) {

        List<Rating> bookRatings = ratings.stream()
                .filter(r -> r.getBookId().equalsIgnoreCase(bookId))
                .collect(Collectors.toList());

        if (bookRatings.isEmpty()) {
            return 0.0;
        }

        double sum = bookRatings.stream()
                .mapToInt(Rating::getRating)
                .sum();

        return sum / bookRatings.size();
    }
}
