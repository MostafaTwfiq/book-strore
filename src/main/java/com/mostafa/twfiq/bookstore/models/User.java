package com.mostafa.twfiq.bookstore.models;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String password;
    private String imagePath;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    private List<Book> books;

    public User(String name, String password, String imagePath) {
        this.name = name;
        this.password = password;
        this.imagePath = imagePath;
    }

    public User(String name, String password, String imagePath, List<Book> books) {
        this.name = name;
        this.password = password;
        this.imagePath = imagePath;
        this.books = books;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "User id: " + id + "\n"
                + "User name: " + name + "\n"
                + "Password: " + password + "\n"
                + "Image path: " + imagePath + "\n"
                + Arrays.toString(books.toArray());
    }
}
