package com.geektext.api.model;

public class AddToCartRequest {
    private Integer userId;
    private Integer bookId;
    private Integer quantity;

    // Constructors
    public AddToCartRequest() {
        this.quantity = 1; // Default quantity
    }

    public AddToCartRequest(Integer userId, Integer bookId, Integer quantity) {
        this.userId = userId;
        this.bookId = bookId;
        this.quantity = quantity != null ? quantity : 1;
    }

    // Getters and Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}