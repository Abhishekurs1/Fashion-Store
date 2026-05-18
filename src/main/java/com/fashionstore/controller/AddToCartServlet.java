package com.fashionstore.controller;

import java.io.IOException;
import java.util.List;

import com.fashionstore.dao.CartDAO;
import com.fashionstore.dao.CartItemDAO;
import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.ProductSizeDAO;
import com.fashionstore.dao.impl.CartDAOImpl;
import com.fashionstore.dao.impl.CartItemDAOImpl;
import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.dao.impl.ProductSizeDAOImpl;
import com.fashionstore.model.Cart;
import com.fashionstore.model.CartItem;
import com.fashionstore.model.Product;
import com.fashionstore.model.ProductSize;
import com.fashionstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final CartDAO cartDAO = new CartDAOImpl();
    private final CartItemDAO cartItemDAO = new CartItemDAOImpl();
    private final ProductDAO productDAO = new ProductDAOImpl();
    private final ProductSizeDAO sizeDAO = new ProductSizeDAOImpl();

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        System.out.println("✅ AddToCartServlet HIT");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loggedUser");

        if (user == null) {
            System.out.println("❌ User not logged in");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String productParam = request.getParameter("productId");
        String sizeLabel = request.getParameter("selectedSize");

        System.out.println("Product ID: " + productParam);
        System.out.println("Selected Size: " + sizeLabel);

        if (productParam == null || productParam.isBlank() || sizeLabel == null || sizeLabel.isBlank()) {
            System.out.println("❌ Missing productId or selectedSize");
            String referer = request.getHeader("Referer");
            if (referer != null && !referer.isBlank()) {
                response.sendRedirect(referer);
            } else {
                response.sendRedirect(request.getContextPath() + "/cart");
            }
            return;
        }

        int productId;
        try {
            productId = Integer.parseInt(productParam);
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid productId");
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        Product product = productDAO.getProductById(productId);
        if (product == null) {
            System.out.println("❌ Product not found");
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        List<ProductSize> sizes = sizeDAO.getSizesByProductId(productId);

        ProductSize selectedSize = null;
        for (ProductSize s : sizes) {
            if (s.getSizeLabel() != null && s.getSizeLabel().equalsIgnoreCase(sizeLabel)) {
                selectedSize = s;
                break;
            }
        }

        if (selectedSize == null) {
            System.out.println("❌ Size not found");
            String referer = request.getHeader("Referer");
            if (referer != null && !referer.isBlank()) {
                response.sendRedirect(referer);
            } else {
                response.sendRedirect(request.getContextPath() + "/cart");
            }
            return;
        }

        Cart cart = cartDAO.getCartByUserId(user.getUserId());
        if (cart == null) {
            System.out.println("Creating new cart...");
            cartDAO.createCart(user.getUserId());
            cart = cartDAO.getCartByUserId(user.getUserId());
        }

        if (cart == null) {
            System.out.println("❌ Cart still null");
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        CartItem existing = cartItemDAO.getCartItem(
                cart.getCartId(),
                productId,
                selectedSize.getProductSizeId()
        );

        if (existing != null) {
            System.out.println("Updating quantity...");
            cartItemDAO.updateQuantity(existing.getCartItemId(), existing.getQuantity() + 1);
        } else {
            System.out.println("Adding new item...");

            CartItem item = new CartItem();
            item.setCartId(cart.getCartId());
            item.setProductId(productId);
            item.setProductSizeId(selectedSize.getProductSizeId());
            item.setQuantity(1);
            item.setPriceAtTime(product.getPrice());

            boolean added = cartItemDAO.addToCart(item);
            System.out.println("Item added: " + added);
        }

        System.out.println("➡ Redirecting to cart page...");
        response.sendRedirect(request.getContextPath() + "/cart");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}