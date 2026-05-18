<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*,java.math.*,com.fashionstore.model.CartItem,com.fashionstore.model.User" %>

<%@ include file="../partials/navbar.jsp" %>

<link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/checkout.css">

<%
    List<CartItem> items = (List<CartItem>) request.getAttribute("cartItems");
    BigDecimal total = (BigDecimal) request.getAttribute("total");

    // ✅ USE DIFFERENT NAME → NO CONFLICT
    User checkoutUser = (User) session.getAttribute("loggedUser");
%>

<div class="checkout-container">

    <!-- LEFT SIDE -->
    <div class="checkout-left">

        <h2>Delivery Details</h2>

        <form action="place-order" method="post">

            <input type="text" name="fullName"
                   value="<%= checkoutUser != null ? checkoutUser.getFullName() : "" %>"
                   placeholder="Full Name" required>

            <input type="text" name="phone"
                   value="<%= checkoutUser != null ? checkoutUser.getPhone() : "" %>"
                   placeholder="Phone" required>

            <textarea name="addressLine1" placeholder="Address Line 1" required>
<%= checkoutUser != null && checkoutUser.getAddressLine1()!=null ? checkoutUser.getAddressLine1() : "" %>
            </textarea>

            <input type="text" name="addressLine2"
                   value="<%= checkoutUser != null && checkoutUser.getAddressLine2()!=null ? checkoutUser.getAddressLine2() : "" %>"
                   placeholder="Address Line 2">

            <div class="row">
                <input type="text" name="city"
                       value="<%= checkoutUser != null && checkoutUser.getCity()!=null ? checkoutUser.getCity() : "" %>"
                       placeholder="City">

                <input type="text" name="state"
                       value="<%= checkoutUser != null && checkoutUser.getState()!=null ? checkoutUser.getState() : "" %>"
                       placeholder="State">
            </div>

            <div class="row">
                <input type="text" name="pincode"
                       value="<%= checkoutUser != null && checkoutUser.getPostalCode()!=null ? checkoutUser.getPostalCode() : "" %>"
                       placeholder="Pincode">

                <input type="text" value="India" readonly>
            </div>

            <!-- PAYMENT -->
            <div class="payment-section">
                <h3>Payment Method</h3>

                <label>
                    <input type="radio" name="paymentMethod" value="COD" checked>
                    Cash on Delivery
                </label>

                <label>
                    <input type="radio" name="paymentMethod" value="ONLINE">
                    Online Payment
                </label>
            </div>

            <button type="submit" class="place-btn">
                Place Order
            </button>

        </form>

    </div>

    <!-- RIGHT SIDE -->
    <div class="checkout-right">

        <h3>Order Summary</h3>

        <% if (items != null) {
            for (CartItem item : items) {

                BigDecimal sub = item.getPriceAtTime()
                        .multiply(new BigDecimal(item.getQuantity()));
        %>

        <div class="summary-item">
            <div>
                <strong><%= item.getProductName() %></strong>
                <p>Qty: <%= item.getQuantity() %></p>
            </div>
            <div>₹ <%= sub %></div>
        </div>

        <% } } %>

        <hr>

        <div class="summary-total">
            <span>Total</span>
            <span>₹ <%= total %></span>
        </div>

    </div>

</div>