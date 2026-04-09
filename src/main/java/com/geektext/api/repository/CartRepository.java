package com.geektext.api.repository;

import com.geektext.api.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    // Find all carts for a specific user
    List<Cart> findByUserId(Integer userId);
}