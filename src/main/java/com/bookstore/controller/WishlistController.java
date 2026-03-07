package com.bookstore.controller;

import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.entity.Book;
import com.bookstore.entity.Wishlist;
import com.bookstore.service.WishlistService;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService service;

    public WishlistController(WishlistService service) { this.service = service; }

    @PostMapping("/create")
    public Wishlist create(@RequestParam Integer userId, @RequestParam String name) {
        return service.createWishlist(userId, name);
    }

    @PostMapping("/add")
    public String addBook(@RequestParam Integer wishlistId, @RequestParam Integer bookId) {
        service.addBook(wishlistId, bookId);
        return "Book added";
    }

    @GetMapping("/{wishlistId}")
    public Set<Book> getBooks(@PathVariable Integer wishlistId) {
        return service.getBooks(wishlistId);
    }
}
