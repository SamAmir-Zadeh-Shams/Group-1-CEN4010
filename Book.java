package com.example.bookservice.model;

public class Book {

    private String title;
    private String author;
    private String genre;
    private double rating;
    private int copiesSold;
    private double price;
    private String publisher;

    public Book() {}

    public Book(String title, String author, String genre,
                double rating, int copiesSold,
                double price, String publisher) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.rating = rating;
        this.copiesSold = copiesSold;
        this.price = price;
        this.publisher = publisher;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getGenre() { return genre; }
    public double getRating() { return rating; }
    public int getCopiesSold() { return copiesSold; }
    public double getPrice() { return price; }
    public String getPublisher() { return publisher; }

    public void setPrice(double price) { this.price = price; }
}

package com.example.bookservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookServiceApplication.class, args);
    }
}





package com.example.bookservice.service;

import com.example.bookservice.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final List<Book> books = new ArrayList<>();

    public BookService() {
        books.add(new Book("Harry Potter", "J.K. Rowling",
                "Fantasy", 4.8, 5000000, 20.0, "Bloomsbury"));

        books.add(new Book("The Hobbit", "J.R.R. Tolkien",
                "Fantasy", 4.7, 4000000, 9.0, "Houghton"));
    }

    public List<Book> getBooksByGenre(String genre) {
        return books.stream()
                .filter(book -> book.getGenre().equalsIgnoreCase(genre))
                .collect(Collectors.toList());
    }

    public List<Book> getTopSellers() {
        return books.stream()
                .sorted(Comparator.comparingInt(Book::getCopiesSold).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    public List<Book> getBooksByRating(double rating) {
        return books.stream()
                .filter(book -> book.getRating() >= rating)
                .collect(Collectors.toList());
    }
}





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
}

