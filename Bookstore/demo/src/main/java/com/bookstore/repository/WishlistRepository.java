package com.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bookstore.entity.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {}
