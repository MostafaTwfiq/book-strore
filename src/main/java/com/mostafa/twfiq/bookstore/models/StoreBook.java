package com.mostafa.twfiq.bookstore.models;

import jakarta.persistence.*;

@Entity
@Table(name = "book_store")
public class StoreBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double price;

    @OneToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    public StoreBook(double price, Book book) {
        this.price = price;
        this.book = book;
    }

    public StoreBook() {

    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "Book in store id: " + id + "\n"
                + "Price: " + price + "\n";
    }
}
