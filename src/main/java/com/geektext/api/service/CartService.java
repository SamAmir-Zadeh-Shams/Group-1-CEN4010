package com.geektext.api.service;

import com.geektext.api.model.Cart;
import com.geektext.api.model.CartItem;
import com.geektext.api.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

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
}