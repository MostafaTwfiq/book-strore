package com.mostafa.twfiq.bookstore.repository;

import com.mostafa.twfiq.bookstore.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query("SELECT book FROM Book book WHERE book.user.id = :id")
    List<Book> findBooksByUserID(@Param("id") int id);

    @Query("SELECT book FROM Book book WHERE book.user.id = :id AND book.id NOT IN (SELECT store_book.book.id FROM StoreBook store_book)")
    List<Book> findUnsoldBooksByUserID(@Param("id") int id);
}
