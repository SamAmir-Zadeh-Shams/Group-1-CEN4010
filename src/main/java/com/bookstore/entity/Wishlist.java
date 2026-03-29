package com.bookstore.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "wishlists",
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "name"}))
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer wishlistId;

    private Integer userId;
    private String name;

    @ManyToMany
    @JoinTable(
        name = "wishlist_books",
        joinColumns = @JoinColumn(name = "wishlist_id"),
        inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> books = new HashSet<>();

    public Integer getWishlistId() { return wishlistId; }
    public void setWishlistId(Integer wishlistId) { this.wishlistId = wishlistId; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Set<Book> getBooks() { return books; }
    public void setBooks(Set<Book> books) { this.books = books; }
}