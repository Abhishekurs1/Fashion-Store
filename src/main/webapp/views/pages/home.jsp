<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.fashionstore.model.Product" %>

<%
    List<Product> products = (List<Product>) request.getAttribute("products");
%>

<link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/home.css?v=<%= System.currentTimeMillis() %>">

<%@ include file="../partials/navbar.jsp" %>

<div class="home-container">

    <!-- 🔥 HERO -->
    <section class="hero">
        <div class="hero-left">
            <span class="badge">New Season Collection</span>
            <h1>Discover Your Style<br>with Fashion Store</h1>
            <p>
                Explore the latest trends in fashion for men, women, kids, and accessories.
                Find your perfect style with our curated collection.
            </p>

            <a href="<%= request.getContextPath() %>/products" class="shop-btn">
                Shop Now
            </a>
        </div>

        <div class="hero-right">
            <div class="hero-card">
                <span>Trendy Fashion Collection</span>
            </div>
        </div>
    </section>

    <!-- 🔥 CATEGORY (FIXED) -->
    <section class="category-section">
        <h2>Shop by Category</h2>

        <div class="category-grid">

            <!-- ✅ USE CATEGORY IDs -->
            <a href="<%= request.getContextPath() %>/products?category=5" class="category-card">
                <h3>Accessories</h3>
                <p>Fashion accessories</p>
                <span>Explore</span>
            </a>

            <a href="<%= request.getContextPath() %>/products?category=3" class="category-card">
                <h3>Kids</h3>
                <p>Fashion for kids</p>
                <span>Explore</span>
            </a>

            <a href="<%= request.getContextPath() %>/products?category=1" class="category-card">
                <h3>Men</h3>
                <p>Fashion for men</p>
                <span>Explore</span>
            </a>

            <a href="<%= request.getContextPath() %>/products?category=2" class="category-card">
                <h3>Women</h3>
                <p>Fashion for women</p>
                <span>Explore</span>
            </a>

        </div>
    </section>

    <!-- 🔥 PRODUCTS -->
    <section class="latest-products">
        <div class="section-header">
            <h2>Latest Products</h2>
            <a href="<%= request.getContextPath() %>/products">View All</a>
        </div>

        <div class="product-grid">

            <%
                if (products != null && !products.isEmpty()) {
                    for (Product p : products) {
            %>

            <a href="<%= request.getContextPath() %>/product?id=<%= p.getProductId() %>" class="product-card">

                <div class="product-image">
                    <img src="<%= request.getContextPath() %>/assets/images/<%= p.getImageUrl() %>"
                         alt="<%= p.getProductName() %>">
                </div>

                <div class="product-info">
                    <h4><%= p.getProductName() %></h4>
                    <p class="brand">Fashion Brand</p>
                    <p class="price">₹ <%= p.getPrice() %></p>

                    <span class="btn-view">View Product</span>
                </div>

            </a>

            <%
                    }
                } else {
            %>
                <p class="empty-text">No products available right now.</p>
            <%
                }
            %>

        </div>
    </section>

</div>

<%@ include file="../partials/footer.jsp" %>