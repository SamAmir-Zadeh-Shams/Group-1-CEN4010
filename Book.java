package com.example.bookservice.model;

public class Book {

    private String title;
    private String author;
    private String genre;
    private double rating;
    private int copiesSold;
    private double price;
    private String publisher;

    public Book() {}

    public Book(String title, String author, String genre,
                double rating, int copiesSold,
                double price, String publisher) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.rating = rating;
        this.copiesSold = copiesSold;
        this.price = price;
        this.publisher = publisher;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getGenre() { return genre; }
    public double getRating() { return rating; }
    public int getCopiesSold() { return copiesSold; }
    public double getPrice() { return price; }
    public String getPublisher() { return publisher; }

    public void setPrice(double price) { this.price = price; }
}
