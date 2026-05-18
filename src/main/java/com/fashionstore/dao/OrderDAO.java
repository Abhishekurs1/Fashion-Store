package com.fashionstore.dao;

import java.util.List;
import com.fashionstore.model.Order;

public interface OrderDAO {

    // Create
    boolean placeOrder(Order order);

    // Read
    Order getOrderById(int orderId);
    List<Order> getOrdersByUserId(int userId);
    List<Order> getAllOrders();

    // Update
    boolean updateOrderStatus(int orderId, String status);
    boolean updatePaymentStatus(int orderId, String paymentStatus);

    // Utility
    List<Order> getOrdersByStatus(String status);
}