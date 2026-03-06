package com.bookstore.service;

import org.springframework.stereotype.Service;
import com.bookstore.entity.*;
import com.bookstore.repository.*;
import java.util.Set;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final BookRepository bookRepository;

    public WishlistService(WishlistRepository wishlistRepository, BookRepository bookRepository) {
        this.wishlistRepository = wishlistRepository;
        this.bookRepository = bookRepository;
    }

    public Wishlist createWishlist(Integer userId, String name) {
        Wishlist w = new Wishlist();
        w.setUserId(userId);
        w.setName(name);
        return wishlistRepository.save(w);
    }

    public void addBook(Integer wishlistId, Integer bookId) {
        Wishlist w = wishlistRepository.findById(wishlistId).orElseThrow();
        Book b = bookRepository.findById(bookId).orElseThrow();
        w.getBooks().add(b);
        wishlistRepository.save(w);
    }

    public Set<Book> getBooks(Integer wishlistId) {
        return wishlistRepository.findById(wishlistId).orElseThrow().getBooks();
    }
}
