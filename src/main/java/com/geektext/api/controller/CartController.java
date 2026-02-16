package com.geektext.api.controller;

import com.geektext.api.model.CartItem;
import com.geektext.api.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {

    @Autowired
    private CartService cartService;

    // GET /api/cart/user/{userId} - Get cart items for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> getCartByUserId(@PathVariable Integer userId) {
        try {
            List<CartItem> cartItems = cartService.getCartItemsByUserId(userId);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("userId", userId);
            response.put("count", cartItems.size());
            response.put("data", cartItems);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", "Failed to retrieve cart items");
            errorResponse.put("message", e.getMessage());

            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    // GET /api/cart/user/{userId}/subtotal - Get cart summary
    @GetMapping("/user/{userId}/subtotal")
    public ResponseEntity<Map<String, Object>> getCartSubtotal(@PathVariable Integer userId) {
        try {
            int itemCount = cartService.getCartItemCount(userId);
            int totalQuantity = cartService.getTotalQuantity(userId);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("userId", userId);
            response.put("itemCount", itemCount);
            response.put("totalQuantity", totalQuantity);
            response.put("note", "Price calculation requires books table with pricing data");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", "Failed to calculate subtotal");

            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}