// Sprint 3: Complete REST API - POST and DELETE endpoints added
package com.geektext.api.controller;

import com.geektext.api.model.AddToCartRequest;
import com.geektext.api.model.CartItem;
import com.geektext.api.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    // POST - Add book to cart
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addToCart(@RequestBody AddToCartRequest request) {
        try {
            // Validation
            if (request.getUserId() == null || request.getBookId() == null) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("error", "userId and bookId are required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }

            if (request.getQuantity() == null || request.getQuantity() < 1) {
                request.setQuantity(1); // Default to 1
            }

            // Add to cart
            CartItem addedItem = cartService.addBookToCart(
                    request.getUserId(),
                    request.getBookId(),
                    request.getQuantity()
            );

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Book added to cart successfully");
            response.put("cartItemId", addedItem.getCartItemId());
            response.put("bookId", addedItem.getBookId());
            response.put("quantity", addedItem.getQuantity());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", "Failed to add book to cart");
            errorResponse.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // DELETE - Remove book from cart
    @DeleteMapping("/remove")
    public ResponseEntity<Map<String, Object>> removeFromCart(
            @RequestParam Integer userId,
            @RequestParam Integer bookId) {
        try {
            // Validation
            if (userId == null || bookId == null) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("error", "userId and bookId are required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }

            // Remove from cart
            boolean removed = cartService.removeBookFromCart(userId, bookId);

            if (removed) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Book removed from cart successfully");
                response.put("userId", userId);
                response.put("bookId", bookId);

                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("error", "Book not found in cart");

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", "Failed to remove book from cart");
            errorResponse.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}