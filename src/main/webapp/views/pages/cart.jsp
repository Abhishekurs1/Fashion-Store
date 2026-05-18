<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.fashionstore.model.CartItem" %>

<%
    List<CartItem> items = (List<CartItem>) request.getAttribute("cartItems");
    if (items == null) {
        items = new java.util.ArrayList<CartItem>();
    }

    BigDecimal total = BigDecimal.ZERO;
    for (CartItem item : items) {
        if (item.getPriceAtTime() != null) {
            total = total.add(item.getPriceAtTime().multiply(new BigDecimal(item.getQuantity())));
        }
    }
%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/cart.css">

<%@ include file="../partials/navbar.jsp" %>

<div class="cart-page">
    <div class="cart-header">
        <div>
            <h2>Your Cart</h2>
            <p class="cart-subtitle">Review your items before checkout</p>
        </div>

        <a class="continue-shopping-btn" href="${pageContext.request.contextPath}/products">
            Continue Shopping
        </a>
    </div>

    <%
        if (items.isEmpty()) {
    %>
        <div class="empty-cart-card">
            <h3>Your cart is empty</h3>
            <p>Add products to see them here.</p>
            <a class="continue-shopping-btn" href="${pageContext.request.contextPath}/products">
                Browse Products
            </a>
        </div>
    <%
        } else {
    %>

    <div class="cart-layout">
        <div class="cart-items">
            <%
                for (CartItem item : items) {
                    BigDecimal subTotal = item.getPriceAtTime().multiply(new BigDecimal(item.getQuantity()));
            %>

            <div class="cart-item-card">
                <div class="cart-item-main">
                    <div class="cart-item-image">
                        <span><%= item.getProductName() != null ? item.getProductName().substring(0, 1).toUpperCase() : "P" %></span>
                    </div>

                    <div class="cart-item-details">
                        <h3><%= item.getProductName() != null ? item.getProductName() : "Product" %></h3>
                        <p>Size: <strong><%= item.getSizeLabel() != null ? item.getSizeLabel() : "N/A" %></strong></p>
                        <p class="item-price">₹ <%= item.getPriceAtTime() %></p>
                    </div>
                </div>

                <div class="cart-item-actions">
                    <form action="${pageContext.request.contextPath}/update-cart-item" method="post" class="qty-form">
                        <input type="hidden" name="cartItemId" value="<%= item.getCartItemId() %>">
                        <label>Qty</label>
                        <input type="number" name="quantity" value="<%= item.getQuantity() %>" min="1" class="qty-input">
                        <button type="submit" class="btn btn-update">Update</button>
                    </form>

                    <form action="${pageContext.request.contextPath}/remove-cart-item" method="post">
                        <input type="hidden" name="cartItemId" value="<%= item.getCartItemId() %>">
                        <button type="submit" class="btn btn-remove">Remove</button>
                    </form>
                </div>

                <div class="cart-item-summary">
                    <span>Subtotal</span>
                    <strong>₹ <%= subTotal %></strong>
                </div>
            </div>

            <%
                }
            %>
        </div>

        <div class="cart-summary-card">
            <h3>Order Summary</h3>

            <div class="summary-row">
                <span>Items</span>
                <span><%= items.size() %></span>
            </div>

            <div class="summary-row">
                <span>Total</span>
                <span>₹ <%= total %></span>
            </div>

            <a href="${pageContext.request.contextPath}/checkout" class="checkout-btn">
    Proceed to Checkout
</a>
        </div>
    </div>

    <%
        }
    %>
</div>

<%@ include file="../partials/footer.jsp" %>