<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.fashionstore.model.Product" %>

<%
    List<Product> products = (List<Product>) request.getAttribute("products");
    Integer currentPageObj = (Integer) request.getAttribute("currentPage");
    Integer totalPagesObj = (Integer) request.getAttribute("totalPages");

    int currentPage = (currentPageObj != null) ? currentPageObj : 1;
    int totalPages = (totalPagesObj != null) ? totalPagesObj : 1;

    String keyword = request.getParameter("keyword");
    String category = request.getParameter("category");
    String min = request.getParameter("min");
    String max = request.getParameter("max");
    String sort = request.getParameter("sort");

    String ctx = request.getContextPath();
%>

<link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/product.css?v=<%= System.currentTimeMillis() %>">

<%@ include file="../partials/navbar.jsp" %>

<div class="product-page">

    <div class="product-layout">

        <!-- FILTERS -->
        <aside class="filter-sidebar">
            <h3>Filters</h3>

            <form action="<%= ctx %>/products" method="get">

                <!-- SEARCH -->
                <div class="filter-group">
                    <label>Search</label>
                    <input type="text" name="keyword" placeholder="Search products..." value="<%= keyword != null ? keyword : "" %>">
                </div>

                <!-- CATEGORY -->
                <div class="filter-group">
                    <label>Category</label>

                    <div class="checkbox-group">
                        <label>
                            <input type="radio" name="category" value="" <%= (category == null || category.isEmpty()) ? "checked" : "" %>> All
                        </label>

                        <label>
                            <input type="radio" name="category" value="1" <%= "1".equals(category) ? "checked" : "" %>> Men
                        </label>

                        <label>
                            <input type="radio" name="category" value="2" <%= "2".equals(category) ? "checked" : "" %>> Women
                        </label>

                        <label>
                            <input type="radio" name="category" value="3" <%= "3".equals(category) ? "checked" : "" %>> Kids
                        </label>

                        <label>
                            <input type="radio" name="category" value="4" <%= "4".equals(category) ? "checked" : "" %>> Footwear
                        </label>

                        <label>
                            <input type="radio" name="category" value="5" <%= "5".equals(category) ? "checked" : "" %>> Accessories
                        </label>
                    </div>
                </div>

                <!-- PRICE -->
                <div class="filter-group">
                    <label>Price Range</label>

                    <div class="price-box">
                        <input type="number" name="min" placeholder="Min ₹" value="<%= min != null ? min : "" %>">
                        <input type="number" name="max" placeholder="Max ₹" value="<%= max != null ? max : "" %>">
                    </div>
                </div>

                <!-- SORT -->
                <div class="filter-group">
                    <label>Sort By</label>
                    <select name="sort">
                        <option value="">Default</option>
                        <option value="low" <%= "low".equals(sort) ? "selected" : "" %>>Price Low → High</option>
                        <option value="high" <%= "high".equals(sort) ? "selected" : "" %>>Price High → Low</option>
                    </select>
                </div>

                <!-- ACTION -->
                <div class="filter-actions">
                    <button type="submit">Apply Filters</button>
                    <a href="<%= ctx %>/products" class="clear-btn">Clear</a>
                </div>

            </form>
        </aside>

        <!-- PRODUCTS -->
        <section class="product-section">
            <div class="product-top">
                <h2>Products</h2>
                <span class="product-count">
                    <%= products != null ? products.size() : 0 %> items found
                </span>
            </div>

            <div class="product-grid">

                <%
                    if (products != null && !products.isEmpty()) {
                        for (Product p : products) {
                %>

                <div class="product-card">

                    <div class="product-image">
                        <img
                            src="<%= ctx %>/assets/images/<%= p.getImageUrl() %>"
                            alt="<%= p.getProductName() %>"
                        />
                    </div>

                    <div class="product-info">
                        <h4><%= p.getProductName() %></h4>
                        <p class="brand">Fashion Brand</p>
                        <p class="price">₹ <%= p.getPrice() %></p>

                        <a href="<%= ctx %>/product?id=<%= p.getProductId() %>"
                           class="btn-view">
                            View Product
                        </a>
                    </div>

                </div>

                <%
                        }
                    } else {
                %>
                    <p>No products found</p>
                <%
                    }
                %>

            </div>

            <!-- PAGINATION -->
            <div class="pagination">
                <%
                    String qKeyword = (keyword != null && !keyword.isEmpty()) ? "&keyword=" + java.net.URLEncoder.encode(keyword, "UTF-8") : "";
                    String qCategory = (category != null && !category.isEmpty()) ? "&category=" + category : "";
                    String qMin = (min != null && !min.isEmpty()) ? "&min=" + min : "";
                    String qMax = (max != null && !max.isEmpty()) ? "&max=" + max : "";
                    String qSort = (sort != null && !sort.isEmpty()) ? "&sort=" + sort : "";

                    for (int i = 1; i <= totalPages; i++) {
                %>

                <a href="<%= ctx %>/products?page=<%= i %><%= qKeyword %><%= qCategory %><%= qMin %><%= qMax %><%= qSort %>"
                   class="<%= (i == currentPage) ? "active" : "" %>">
                    <%= i %>
                </a>

                <%
                    }
                %>
            </div>

        </section>
    </div>
</div>

<%@ include file="../partials/footer.jsp" %>