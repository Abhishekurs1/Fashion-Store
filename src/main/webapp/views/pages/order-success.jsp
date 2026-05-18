<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>

<%@ include file="../partials/navbar.jsp" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/order-success.css">

<%
    Integer orderId = (Integer) request.getAttribute("orderId");
    Object date = request.getAttribute("date");
    String payment = (String) request.getAttribute("payment");
    String status = (String) request.getAttribute("status");
    Object total = request.getAttribute("total");
    String address = (String) request.getAttribute("address");

    List<Map<String, Object>> items =
        (List<Map<String, Object>>) request.getAttribute("items");
%>

<div class="page">

    <div class="card">

        <!-- HEADER -->
        <div class="header">
            <div class="icon">✔</div>
            <h2>Order Placed Successfully</h2>
            <p>Your order has been placed successfully and is being processed</p>
        </div>

        <!-- ORDER DETAILS -->
        <div class="box">

            <div class="row">
                <span>Order ID</span>
                <span>#<%= orderId %></span>
            </div>

            <div class="row">
                <span>Order Date</span>
                <span><%= date %></span>
            </div>

            <div class="row">
                <span>Payment Method</span>
                <span><%= payment %></span>
            </div>

            <div class="row">
                <span>Status</span>
                <span class="status"><%= status %></span>
            </div>

            <div class="row total">
                <span>Total Amount</span>
                <span>₹ <%= total %></span>
            </div>

        </div>

        <!-- DELIVERY -->
        <div class="box">
            <h3>Delivery Details</h3>
            <p><%= address %></p>
        </div>

        <!-- ITEMS -->
        <div class="box">
            <h3>Ordered Items</h3>

            <% if (items != null) {
                for (Map<String, Object> item : items) { %>

            <div class="item">
                <div>
                    <strong><%= item.get("name") %></strong>
                    <p>Qty: <%= item.get("qty") %></p>
                </div>
                <div class="price">
                    ₹ <%= item.get("price") %>
                </div>
            </div>

            <% } } %>

        </div>

        <!-- ACTION -->
        <div class="actions">
            <a href="home" class="btn-primary">Continue Shopping</a>
            <a href="cart" class="btn-secondary">Go to Cart</a>
        </div>

    </div>

</div>