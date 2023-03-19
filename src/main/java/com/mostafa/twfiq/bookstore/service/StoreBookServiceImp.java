package com.mostafa.twfiq.bookstore.service;

import com.mostafa.twfiq.bookstore.models.StoreBook;
import com.mostafa.twfiq.bookstore.repository.StoreBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreBookServiceImp implements StoreBookService {

    @Autowired
    private StoreBookRepository storeBookRepository;

    @Override
    public StoreBook saveStoreBook(StoreBook storeBook) {
        return storeBookRepository.save(storeBook);
    }

    @Override
    public void delete(StoreBook storeBook) {
        storeBookRepository.delete(storeBook);
    }

    @Override
    public List<StoreBook> findAll() {
        return storeBookRepository.findAll();
    }

    @Override
    public StoreBook findStoreBookById(int id) {
        Optional<StoreBook> storeBookOptional = storeBookRepository.findById(id);
        return storeBookOptional.orElse(null);
    }

    @Override
    public List<StoreBook> findStoreBookByBookID(int id) {
        return storeBookRepository.findStoreBooksByBookId(id);
    }

    @Override
    public List<StoreBook> findStoreBookByUserID(int id) {
        return storeBookRepository.findStoreBooksByUserId(id);
    }

}
