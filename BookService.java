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
                "Fantasy", 4.7, 4000000, 15.0, "Houghton"));

        books.add(new Book("1984", "George Orwell",
                "Dystopian", 4.6, 3000000, 12.0, "Penguin"));
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

    public void applyDiscountByPublisher(String publisher, double discountPercent) {
        for (Book book : books) {
            if (book.getPublisher().equalsIgnoreCase(publisher)) {
                double currentPrice = book.getPrice();
                double discountAmount = currentPrice * (discountPercent / 100);
                book.setPrice(currentPrice - discountAmount);
            }
        }
    }

    public List<Book> getAllBooks() {
        return books;
    }
}
