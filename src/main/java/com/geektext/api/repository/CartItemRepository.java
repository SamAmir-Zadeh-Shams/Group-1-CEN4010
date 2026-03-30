package com.geektext.api.repository;

import com.geektext.api.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.cart.cartId IN (SELECT c.cartId FROM Cart c WHERE c.userId = :userId) AND ci.bookId = :bookId")
    int deleteByUserIdAndBookId(Integer userId, Integer bookId);
}