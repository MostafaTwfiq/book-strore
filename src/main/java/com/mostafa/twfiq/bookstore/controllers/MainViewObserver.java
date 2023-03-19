package com.mostafa.twfiq.bookstore.controllers;

import com.mostafa.twfiq.bookstore.models.User;

public interface MainViewObserver {

    void updateSections(User user);

    void updateSections();

    void search(String searchString);

}
