<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.fashionstore.model.User" %>
<%@ page import="com.fashionstore.dao.CartDAO" %>
<%@ page import="com.fashionstore.dao.impl.CartDAOImpl" %>
<%@ page import="com.fashionstore.model.Cart" %>

<%
    User user = (User) session.getAttribute("loggedUser");

    int cartCount = 0;
    String userInitial = "U";

    if (user != null) {
        try {
            if (user.getFullName() != null && !user.getFullName().trim().isEmpty()) {
                userInitial = user.getFullName().trim().substring(0, 1).toUpperCase();
            }

            CartDAO cartDAO = new CartDAOImpl();
            Cart cart = cartDAO.getCartByUserId(user.getUserId());
            if (cart != null) {
                cartCount = cartDAO.getCartItemCount(cart.getCartId());
            }
        } catch (Exception e) {
            cartCount = 0;
        }
    }
%>

<link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/navbar.css?v=<%= System.currentTimeMillis() %>">

<div class="header-bg">
    <div class="main-container">
        <div class="navbar">

            <div class="nav-left">
                <a href="<%= request.getContextPath() %>/home" class="logo">
                    Fashion<span>Store</span>
                </a>
            </div>

            <div class="nav-menu">
                <a href="<%= request.getContextPath() %>/home">Home</a>
                <a href="<%= request.getContextPath() %>/products">Products</a>
            </div>

            <div class="nav-right">

                <form action="<%= request.getContextPath() %>/search" method="get" class="search-form">
                    <input type="text" name="keyword" placeholder="Search products..." class="search-box">
                    <button type="submit" class="search-btn">Search</button>
                </form>

                <a href="<%= request.getContextPath() %>/cart" class="cart-link">
                    <span class="cart-icon">🛒</span>
                    <span class="cart-text">Cart</span>
                    <span class="cart-count"><%= cartCount %></span>
                </a>

                <% if (user != null) { %>
                    <a href="<%= request.getContextPath() %>/profile" class="profile-icon" title="Profile">
                        <%= userInitial %>
                    </a>
                <% } else { %>
                    <a href="<%= request.getContextPath() %>/login" class="auth-link">Login</a>
                <% } %>

            </div>

        </div>
    </div>
</div>