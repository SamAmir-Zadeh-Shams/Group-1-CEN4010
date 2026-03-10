package com.example.bookservice.controller;

import com.example.bookservice.model.Book;
import com.example.bookservice.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/genre")
    public List<Book> getBooksByGenre(@RequestParam String genre) {
        return bookService.getBooksByGenre(genre);
    }

    @GetMapping("/top-sellers")
    public List<Book> getTopSellers() {
        return bookService.getTopSellers();
    }

    @GetMapping("/rating")
    public List<Book> getBooksByRating(@RequestParam double rating) {
        return bookService.getBooksByRating(rating);
    }

    @PatchMapping("/discount")
    public void discountBooksByPublisher(
            @RequestParam String publisher,
            @RequestParam double discountPercent) {
        bookService.applyDiscountByPublisher(publisher, discountPercent);
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }
}
