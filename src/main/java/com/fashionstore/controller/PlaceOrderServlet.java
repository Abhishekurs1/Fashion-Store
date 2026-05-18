package com.fashionstore.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;

import com.fashionstore.dao.*;
import com.fashionstore.dao.impl.*;
import com.fashionstore.model.*;
import com.fashionstore.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/place-order")
public class PlaceOrderServlet extends HttpServlet {

    private CartDAO cartDAO = new CartDAOImpl();
    private CartItemDAO cartItemDAO = new CartItemDAOImpl();

    // 🔥 ADD THIS
    private ProductSizeDAO sizeDAO = new ProductSizeDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loggedUser") == null) {
            response.sendRedirect("login");
            return;
        }

        User user = (User) session.getAttribute("loggedUser");

        // ADDRESS
        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");
        String addressLine1 = request.getParameter("addressLine1");
        String addressLine2 = request.getParameter("addressLine2");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String pincode = request.getParameter("pincode");

        String fullAddress =
                safe(fullName) + ", " +
                safe(phone) + ", " +
                safe(addressLine1) + ", " +
                safe(addressLine2) + ", " +
                safe(city) + ", " +
                safe(state) + " - " +
                safe(pincode);

        int orderId = 0;

        try (Connection con = DBConnection.getConnection()) {

            Cart cart = cartDAO.getCartByUserId(user.getUserId());

            if (cart == null) {
                response.sendRedirect("cart");
                return;
            }

            var items = cartItemDAO.getCartItems(cart.getCartId());

            if (items == null || items.isEmpty()) {
                response.sendRedirect("cart");
                return;
            }

            // 🔥 CHECK STOCK BEFORE ORDER
            for (CartItem item : items) {

                ProductSize size =
                        sizeDAO.getSizeById(item.getProductSizeId());

                if (size == null ||
                    size.getStockQuantity() < item.getQuantity()) {

                    session.setAttribute(
                        "stockError",
                        item.getProductName() + " is out of stock!"
                    );

                    response.sendRedirect("cart");
                    return;
                }
            }

            BigDecimal total = BigDecimal.ZERO;

            for (CartItem item : items) {
                total = total.add(
                        item.getPriceAtTime()
                            .multiply(BigDecimal.valueOf(item.getQuantity()))
                );
            }

            // 🔥 INSERT ORDER
            String orderSql =
                    "INSERT INTO orders " +
                    "(user_id, total_amount, payment_method, payment_status, order_status, shipping_address) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps =
                    con.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, user.getUserId());
            ps.setBigDecimal(2, total);

            String paymentMethod =
                    request.getParameter("paymentMethod");

            ps.setString(3, paymentMethod);
            ps.setString(4, "pending");
            ps.setString(5, "placed");
            ps.setString(6, fullAddress);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            rs.next();

            orderId = rs.getInt(1);

            // 🔥 INSERT ITEMS
            String itemSql =
                    "INSERT INTO order_items " +
                    "(order_id, product_id, product_size_id, product_name, selected_size, quantity, price_at_time, total_price) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement itemPs =
                    con.prepareStatement(itemSql);

            for (CartItem item : items) {

                BigDecimal subTotal =
                        item.getPriceAtTime()
                                .multiply(BigDecimal.valueOf(item.getQuantity()));

                itemPs.setInt(1, orderId);
                itemPs.setInt(2, item.getProductId());
                itemPs.setInt(3, item.getProductSizeId());
                itemPs.setString(4, item.getProductName());
                itemPs.setString(5, item.getSizeLabel());
                itemPs.setInt(6, item.getQuantity());
                itemPs.setBigDecimal(7, item.getPriceAtTime());
                itemPs.setBigDecimal(8, subTotal);

                itemPs.executeUpdate();

                // 🔥 REDUCE STOCK
                sizeDAO.reduceStock(
                        item.getProductSizeId(),
                        item.getQuantity()
                );

                // 🔥 AUTO DISABLE IF STOCK 0
                ProductSize updatedSize =
                        sizeDAO.getSizeById(item.getProductSizeId());

                if (updatedSize.getStockQuantity() <= 0) {

                    updatedSize.setAvailable(false);

                    sizeDAO.updateProductSize(updatedSize);
                }
            }

            // 🔥 CLEAR CART
            cartItemDAO.clearCart(cart.getCartId());

            session.setAttribute("lastOrderId", orderId);

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("order-success?orderId=" + orderId);
    }

    private String safe(String value) {
        return value != null ? value : "";
    }
}