// Sprint 2: Shopping cart service - database entities and GET endpoints
package com.geektext.api.service;

import com.geektext.api.model.Cart;
import com.geektext.api.model.CartItem;
import com.geektext.api.repository.CartRepository;
import com.geektext.api.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;  // NEW

    // Get all cart items for a user
    public List<CartItem> getCartItemsByUserId(Integer userId) {
        List<Cart> carts = cartRepository.findByUserId(userId);
        List<CartItem> allItems = new ArrayList<>();

        for (Cart cart : carts) {
            allItems.addAll(cart.getCartItems());
        }

        return allItems;
    }

    // Get cart count for a user
    public int getCartItemCount(Integer userId) {
        List<CartItem> items = getCartItemsByUserId(userId);
        return items.size();
    }

    // Calculate total quantity
    public int getTotalQuantity(Integer userId) {
        List<CartItem> items = getCartItemsByUserId(userId);
        int total = 0;
        for (CartItem item : items) {
            total += item.getQuantity();
        }
        return total;
    }

    // Add book to cart
    @Transactional
    public CartItem addBookToCart(Integer userId, Integer bookId, Integer quantity) {
        // Find or create cart for user
        List<Cart> userCarts = cartRepository.findByUserId(userId);
        Cart cart;

        if (userCarts.isEmpty()) {
            // Create new cart for user
            cart = new Cart(userId);
            cart = cartRepository.save(cart);
        } else {
            // Use existing cart
            cart = userCarts.get(0);
        }

        // Check if book already in cart
        for (CartItem existingItem : cart.getCartItems()) {
            if (existingItem.getBookId().equals(bookId)) {
                // Book already in cart - increase quantity
                existingItem.setQuantity(existingItem.getQuantity() + quantity);
                return cartItemRepository.save(existingItem);  // Save and return with ID
            }
        }

        // Book not in cart - add new item
        CartItem newItem = new CartItem(cart, bookId, quantity);
        return cartItemRepository.save(newItem);  // Save and return with generated ID
    }

    // Remove book from cart
    @Transactional
    public boolean removeBookFromCart(Integer userId, Integer bookId) {
        int deletedCount = cartItemRepository.deleteByUserIdAndBookId(userId, bookId);
        return deletedCount > 0;
    }
}