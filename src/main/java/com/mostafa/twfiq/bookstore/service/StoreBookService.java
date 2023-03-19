package com.mostafa.twfiq.bookstore.service;

import com.mostafa.twfiq.bookstore.models.StoreBook;

import java.util.List;

public interface StoreBookService {

    StoreBook saveStoreBook(StoreBook storeBook);

    void delete(StoreBook storeBook);

    List<StoreBook> findAll();

    StoreBook findStoreBookById(int id);

    List<StoreBook> findStoreBookByBookID(int id);

    List<StoreBook> findStoreBookByUserID(int id);

}
