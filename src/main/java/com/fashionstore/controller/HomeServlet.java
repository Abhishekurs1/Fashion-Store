package com.fashionstore.controller;

import java.io.IOException;
import java.util.List;

import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private ProductDAO productDAO;

    @Override
    public void init() {
        productDAO = new ProductDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Product> products = null;

        try {
            String keyword = request.getParameter("keyword");
            String category = request.getParameter("category");
            String min = request.getParameter("min");
            String max = request.getParameter("max");

            if (min != null && max != null && !min.isEmpty() && !max.isEmpty()) {
                try {
                    double minPrice = Double.parseDouble(min);
                    double maxPrice = Double.parseDouble(max);
                    products = productDAO.getProductsByPriceRange(minPrice, maxPrice);
                } catch (NumberFormatException e) {
                    products = productDAO.getAllProducts();
                }
            } else if (category != null && !category.isEmpty()) {
                switch (category) {
                    case "Men":
                        products = productDAO.getProductsByCategory(1);
                        break;
                    case "Women":
                        products = productDAO.getProductsByCategory(2);
                        break;
                    case "Kids":
                        products = productDAO.getProductsByCategory(3);
                        break;
                    default:
                        products = productDAO.getAllProducts();
                        break;
                }
            } else if (keyword != null && !keyword.trim().isEmpty()) {
                products = productDAO.searchProducts(keyword);
            } else {
                products = productDAO.getAllProducts();
            }

            if (products == null) {
                products = productDAO.getAllProducts();
            }

            // ✅ LIMIT HOME PAGE LATEST PRODUCTS TO 12
            if (products.size() > 12) {
                products = products.subList(0, 12);
            }

            System.out.println("Products fetched for home: " + products.size());
            request.setAttribute("products", products);

        } catch (Exception e) {
            e.printStackTrace();
            List<Product> fallback = productDAO.getAllProducts();
            if (fallback.size() > 12) {
                fallback = fallback.subList(0, 12);
            }
            request.setAttribute("products", fallback);
        }

        request.getRequestDispatcher("/views/pages/home.jsp")
               .forward(request, response);
    }
}