package com.bookstore.profilemanagement.repository;

import com.bookstore.profilemanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
