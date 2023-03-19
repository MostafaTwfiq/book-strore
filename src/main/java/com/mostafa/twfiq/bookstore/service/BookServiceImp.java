package com.mostafa.twfiq.bookstore.service;

import com.mostafa.twfiq.bookstore.models.Book;
import com.mostafa.twfiq.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImp implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findBooksByUserID(int id) {
        return bookRepository.findBooksByUserID(id);
    }

    @Override
    public void delete(Book book) {
        bookRepository.deleteById(book.getId());
    }

    @Override
    public List<Book> findUnsoldBooksByUserID(int id) {
        return bookRepository.findUnsoldBooksByUserID(id);
    }
}
