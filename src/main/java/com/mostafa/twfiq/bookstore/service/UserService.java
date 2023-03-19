package com.mostafa.twfiq.bookstore.service;

import com.mostafa.twfiq.bookstore.models.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);

    List<User> findUsersByName(String name);
}
