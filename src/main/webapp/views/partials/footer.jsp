<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/footer.css?v=<%= System.currentTimeMillis() %>">

<footer class="site-footer">
    <div class="footer-inner">

        <div class="footer-brand">
            <h2 class="footer-logo">Fashion<span>Store</span></h2>
            <p>
                Modern fashion for everyone. Discover your style with us and shop the latest trends with comfort and confidence.
            </p>

            <div class="footer-badges">
                <span>Secure Checkout</span>
                <span>Fast Delivery</span>
                <span>Easy Returns</span>
            </div>
        </div>

        <div class="footer-column">
            <h3>Why Shop With Us</h3>
            <div class="footer-info">
                <p>Free shipping on selected orders</p>
                <p>Premium quality fashion products</p>
                <p>Cash on delivery available</p>
                <p>Support for order-related help</p>
            </div>
        </div>

        <div class="footer-column">
            <h3>Contact</h3>
            <div class="footer-info">
                <p>Email: abhishekurs123456@gmail.com</p>
                <p>Phone: +91 90715 56097</p>
                <p>Mon - Sat: 9:00 AM - 7:00 PM</p>
                <p>India</p>
            </div>
        </div>

        <div class="footer-column">
            <h3>Stay Connected</h3>
            <p class="footer-note">Get updates on new arrivals, offers, and seasonal collections.</p>

            <form class="subscribe-form" action="#" method="post">
                <input type="email" placeholder="Enter your email" />
                <button type="submit">Subscribe</button>
            </form>

            <div class="social-links">
                <a href="#" aria-label="Instagram">Instagram</a>
                <a href="#" aria-label="Facebook">Facebook</a>
                <a href="#" aria-label="X">X</a>
            </div>
        </div>

    </div>

    <div class="footer-bottom">
        © 2026 FashionStore. All rights reserved.
    </div>
</footer>