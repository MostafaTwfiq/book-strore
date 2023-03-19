package com.mostafa.twfiq.bookstore.models;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String author;
    private String imagePath;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @OneToOne(mappedBy = "book")
    private StoreBook storeBook;
    @Enumerated(EnumType.STRING)
    private BookCategory category;

    public Book(String name, String author, String imagePath, User user, StoreBook storeBook, BookCategory category) {
        this.name = name;
        this.author = author;
        this.imagePath = imagePath;
        this.user = user;
        this.storeBook = storeBook;
        this.category = category;
    }

    public Book(String name, String author, String imagePath, User user, BookCategory category) {
        this.name = name;
        this.author = author;
        this.imagePath = imagePath;
        this.user = user;
        this.category = category;
    }

    public Book() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookCategory getCategory() {
        return category;
    }

    public void setCategory(BookCategory category) {
        this.category = category;
    }

    public StoreBook getStoreBook() {
        return storeBook;
    }

    public void setStoreBook(StoreBook storeBook) {
        this.storeBook = storeBook;
    }

    @Override
    public String toString() {
        return "Book id: " + id + "\n"
                + "Book name: " + name + "\n"
                + "author: " + author + "\n"
                + "Image path: " + imagePath + "\n"
                + "Category: " + category + "\n"
                + "Book in store: " + storeBook;
    }
}
