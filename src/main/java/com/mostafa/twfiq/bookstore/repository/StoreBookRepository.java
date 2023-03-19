package com.mostafa.twfiq.bookstore.repository;

import com.mostafa.twfiq.bookstore.models.StoreBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreBookRepository extends JpaRepository<StoreBook, Integer> {

    @Query("SELECT store_book FROM StoreBook store_book WHERE store_book.book.id = :id")
    List<StoreBook> findStoreBooksByBookId(@Param("id") int id);

    @Query("SELECT store_book FROM StoreBook store_book WHERE store_book.book.user.id = :id")
    List<StoreBook> findStoreBooksByUserId(@Param("id") int id);

}
