package com.fashionstore.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.fashionstore.dao.*;
import com.fashionstore.dao.impl.*;
import com.fashionstore.model.*;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    private CartDAO cartDAO = new CartDAOImpl();
    private CartItemDAO cartItemDAO = new CartItemDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loggedUser") == null) {
            response.sendRedirect("login");
            return;
        }

        User user = (User) session.getAttribute("loggedUser");

        Cart cart = cartDAO.getCartByUserId(user.getUserId());

        if (cart == null) {
            response.sendRedirect("cart");
            return;
        }

        List<CartItem> items = cartItemDAO.getCartItems(cart.getCartId());

        if (items == null || items.isEmpty()) {
            response.sendRedirect("cart");
            return;
        }

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem item : items) {
            total = total.add(
                    item.getPriceAtTime().multiply(BigDecimal.valueOf(item.getQuantity()))
            );
        }

        // 🔥 SEND DATA TO JSP
        request.setAttribute("cartItems", items);
        request.setAttribute("total", total);
        request.setAttribute("user", user);

        request.getRequestDispatcher("/views/pages/checkout.jsp")
               .forward(request, response);
    }
}