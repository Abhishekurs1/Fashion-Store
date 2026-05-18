package com.fashionstore.dao;

import com.fashionstore.model.Cart;

public interface CartDAO {

    int getCartItemCount(int cartId);

    // Create
    boolean createCart(int userId);

    // Read
    Cart getCartByUserId(int userId);
    Cart getCartById(int cartId);

    // Utility
    boolean isCartExists(int userId);

    // Delete
    boolean deleteCart(int cartId);
}