package com.mostafa.twfiq.bookstore.controllers.cards;

import com.mostafa.twfiq.bookstore.controllers.MainViewObserver;
import com.mostafa.twfiq.bookstore.models.StoreBook;
import com.mostafa.twfiq.bookstore.models.User;

public interface StoreBookCard {

    void setCardData(User user, StoreBook storeBook, MainViewObserver mainViewObserver);

}
