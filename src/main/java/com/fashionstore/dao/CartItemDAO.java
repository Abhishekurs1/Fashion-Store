package com.fashionstore.dao;

import java.util.List;
import com.fashionstore.model.CartItem;

public interface CartItemDAO {

    // Create
    boolean addToCart(CartItem item);

    // Read
    List<CartItem> getCartItems(int cartId);
    CartItem getCartItemById(int cartItemId);
    CartItem getCartItem(int cartId, int productId, int productSizeId);

    // Update
    boolean updateCartItem(CartItem item);
    boolean updateQuantity(int cartItemId, int quantity);

    // Delete
    boolean removeCartItem(int cartItemId);
    boolean clearCart(int cartId);

    // Utility
    boolean isProductInCart(int cartId, int productId, int productSizeId);
}