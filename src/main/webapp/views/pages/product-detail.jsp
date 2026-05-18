<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.fashionstore.model.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="com.fashionstore.model.ProductSize" %>

<%
    Product product = (Product) request.getAttribute("product");
    List<ProductSize> sizes = (List<ProductSize>) request.getAttribute("sizes");
    List<Product> related = (List<Product>) request.getAttribute("relatedProducts");
%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/product-detail.css?v=<%=System.currentTimeMillis()%>">

<%@ include file="../partials/navbar.jsp" %>

<div class="product-detail-container">

    <!-- ================= MAIN PRODUCT ================= -->
    <div class="product-detail-card">

        <!-- IMAGE -->
        <div class="product-image-box">
            <img src="${pageContext.request.contextPath}/assets/images/<%= product.getImageUrl() %>"
                 alt="<%= product.getProductName() %>">
        </div>

        <!-- INFO -->
        <div class="product-info-box">

            <h2><%= product.getProductName() %></h2>

            <p class="product-desc">
                <%= product.getDescription() != null ? product.getDescription() : "No description available." %>
            </p>

            <h3 class="product-price">₹ <%= product.getPrice() %></h3>

            <!-- FORM -->
            <form id="cartForm" action="${pageContext.request.contextPath}/add-to-cart" method="post">

                <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                <input type="hidden" name="selectedSize" id="selectedSize">

                <!-- SIZE -->
                <!-- SIZE -->
<div class="size-section">
    <h4>Select Size</h4>

    <div class="size-options">

        <%
            if (sizes != null && !sizes.isEmpty()) {

                for (ProductSize s : sizes) {

                    boolean outOfStock =
                        !s.isAvailable() ||
                        s.getStockQuantity() <= 0;
        %>

        <button
            type="button"
            class="size-btn <%= outOfStock ? "disabled" : "" %>"
            data-size="<%= s.getSizeLabel() %>"
            <%= outOfStock ? "disabled" : "" %>>

            <%= s.getSizeLabel() %>

            <%= outOfStock ? " (Out of Stock)" : "" %>

        </button>

        <%
                }

            } else {
        %>

        <p>No sizes available</p>

        <%
            }
        %>

    </div>
</div>

                <!-- BUTTON -->
                <div class="action-buttons">
                    <button type="button" onclick="submitCart()" class="add-cart-btn">
                        Add to Cart
                    </button>
                </div>

            </form>

        </div>
    </div>


    <!-- ================= RELATED PRODUCTS ================= -->
    <div class="related-section">

    <h3>Related Products</h3>

    <div class="related-grid">

        <%
            if (related != null && !related.isEmpty()) {
                for (Product p : related) {
        %>

        <div class="related-card">

            <div class="related-image">
                <img src="<%=request.getContextPath()%>/assets/images/<%=p.getImageUrl()%>">
            </div>

            <h4><%= p.getProductName() %></h4>

            <p class="price">₹ <%= p.getPrice() %></p>

            <!-- 🔥 PROPER BUTTON -->
            <a href="<%=request.getContextPath()%>/product?id=<%=p.getProductId()%>" 
               class="related-btn">
               View Product
            </a>

        </div>

        <%
                }
            } else {
        %>
            <p class="empty-text">No related products found</p>
        <%
            }
        %>

    </div>
</div>
    </div>

</div>


<!-- ================= JS ================= -->
<script>
document.addEventListener("DOMContentLoaded", function () {

    const sizeButtons = document.querySelectorAll(".size-btn");
    const sizeInput = document.getElementById("selectedSize");

    sizeButtons.forEach(btn => {
        btn.addEventListener("click", function () {

            if (this.classList.contains("disabled")) return;

            sizeButtons.forEach(b => b.classList.remove("active"));
            this.classList.add("active");

            sizeInput.value = this.getAttribute("data-size");
        });
    });

});

function submitCart() {
    const size = document.getElementById("selectedSize").value;

    if (!size) {
        alert("Please select a size before adding to cart!");
        return;
    }

    document.getElementById("cartForm").submit();
}
</script>

<%@ include file="../partials/footer.jsp" %>