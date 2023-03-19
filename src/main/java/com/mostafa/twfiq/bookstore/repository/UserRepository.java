package com.mostafa.twfiq.bookstore.repository;

import com.mostafa.twfiq.bookstore.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT user FROM User user WHERE user.name = :name")
    List<User> findUsersByName(@Param("name") String name);
}
