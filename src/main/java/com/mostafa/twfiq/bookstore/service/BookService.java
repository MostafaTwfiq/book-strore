package com.mostafa.twfiq.bookstore.service;

import com.mostafa.twfiq.bookstore.models.Book;

import java.util.List;

public interface BookService {

    Book saveBook(Book book);

    List<Book> findBooksByUserID(int id);

    void delete(Book book);

    List<Book> findUnsoldBooksByUserID(int id);
}
