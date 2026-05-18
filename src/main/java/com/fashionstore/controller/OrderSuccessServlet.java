package com.fashionstore.controller;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import com.fashionstore.util.DBConnection;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/order-success")
public class OrderSuccessServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String orderIdParam = request.getParameter("orderId");

        if (orderIdParam == null) {
            response.sendRedirect("home");
            return;
        }

        int orderId = Integer.parseInt(orderIdParam);

        try (Connection con = DBConnection.getConnection()) {

            // 🔹 GET ORDER
            String orderSql = "SELECT * FROM orders WHERE order_id=?";
            PreparedStatement ps = con.prepareStatement(orderSql);
            ps.setInt(1, orderId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                request.setAttribute("orderId", rs.getInt("order_id"));
                request.setAttribute("date", rs.getTimestamp("created_at"));
                request.setAttribute("payment", rs.getString("payment_method"));
                request.setAttribute("status", rs.getString("order_status"));
                request.setAttribute("total", rs.getBigDecimal("total_amount"));
                request.setAttribute("address", rs.getString("shipping_address"));
            }

            // 🔹 GET ITEMS
            String itemSql = "SELECT * FROM order_items WHERE order_id=?";
            PreparedStatement itemPs = con.prepareStatement(itemSql);
            itemPs.setInt(1, orderId);

            ResultSet itemRs = itemPs.executeQuery();

            List<Map<String, Object>> items = new ArrayList<>();

            while (itemRs.next()) {
                Map<String, Object> item = new HashMap<>();

                item.put("name", itemRs.getString("product_name"));
                item.put("qty", itemRs.getInt("quantity"));
                item.put("price", itemRs.getBigDecimal("price_at_time"));

                items.add(item);
            }

            request.setAttribute("items", items);

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("/views/pages/order-success.jsp")
               .forward(request, response);
    }
}