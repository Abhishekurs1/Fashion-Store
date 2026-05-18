<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.fashionstore.model.Order" %>

<%
    List<Order> orders = (List<Order>) request.getAttribute("orders");
    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, hh:mm a");
%>

<link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/order-history.css?v=<%= System.currentTimeMillis() %>">

<%@ include file="../partials/navbar.jsp" %>

<div class="orders-page">
    <div class="orders-container">

        <div class="orders-header">
            <h2>My Orders</h2>
            <p>Track your previous purchases and order status</p>
        </div>

        <%
            if (orders != null && !orders.isEmpty()) {
                for (Order o : orders) {

                    String status = o.getOrderStatus() != null ? o.getOrderStatus() : "placed";
                    String statusClass = "status-placed";

                    if ("delivered".equalsIgnoreCase(status)) {
                        statusClass = "status-delivered";
                    } else if ("shipped".equalsIgnoreCase(status)) {
                        statusClass = "status-shipped";
                    } else if ("cancelled".equalsIgnoreCase(status)) {
                        statusClass = "status-cancelled";
                    }

                    String formattedDate = "";
                    if (o.getCreatedAt() != null) {
                        formattedDate = sdf.format(o.getCreatedAt());
                    }
        %>

        <div class="order-card">

            <div class="order-top">
                <div>
                    <h3>Order #<%= o.getOrderId() %></h3>
                    <span class="order-date"><%= formattedDate %></span>
                </div>

                <span class="status-badge <%= statusClass %>">
                    <%= status %>
                </span>
            </div>

            <div class="order-mid">
                <div class="order-info-box">
                    <label>Payment</label>
                    <p><%= o.getPaymentMethod() %></p>
                </div>

                <div class="order-info-box">
                    <label>Total</label>
                    <p class="total-price">₹ <%= o.getTotalAmount() %></p>
                </div>

                <div class="order-info-box">
                    <label>Payment Status</label>
                    <p><%= o.getPaymentStatus() %></p>
                </div>
            </div>

            <div class="order-bottom">
                <a href="<%= request.getContextPath() %>/order-success?orderId=<%= o.getOrderId() %>" class="view-btn">
                    View Details
                </a>
            </div>

        </div>

        <%
                }
            } else {
        %>

        <div class="empty-orders">
            <h3>No orders found</h3>
            <p>You have not placed any orders yet.</p>
            <a href="<%= request.getContextPath() %>/products" class="shop-btn">Start Shopping</a>
        </div>

        <%
            }
        %>

    </div>
</div>

<%@ include file="../partials/footer.jsp" %>