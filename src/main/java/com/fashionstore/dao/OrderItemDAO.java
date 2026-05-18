package com.fashionstore.dao;

import java.util.List;
import com.fashionstore.model.OrderItem;

public interface OrderItemDAO {

    // 1. Add single item
    boolean addOrderItem(OrderItem item);

    // 2. Add multiple items (for placing order)
    boolean addOrderItems(List<OrderItem> items);

    // 3. Get all items for a specific order
    List<OrderItem> getItemsByOrderId(int orderId);

    // 4. Get single item by ID
    OrderItem getOrderItemById(int orderItemId);

    // 5. Get total amount of an order
    double getTotalAmountByOrderId(int orderId);

    // 6. Get total quantity of items in an order
    int getTotalQuantityByOrderId(int orderId);
}