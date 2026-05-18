package com.fashionstore.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    private ProductDAO productDAO;

    @Override
    public void init() {
        productDAO = new ProductDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String keyword = request.getParameter("keyword");
            String category = request.getParameter("category");
            String min = request.getParameter("min");
            String max = request.getParameter("max");
            String sort = request.getParameter("sort");
            String pageParam = request.getParameter("page");

            int page = 1;
            int pageSize = 12;

            if (pageParam != null && !pageParam.isEmpty()) {
                page = Integer.parseInt(pageParam);
            }

            // Load base data
            List<Product> products;

            if (category != null && !category.isEmpty()) {
                int catId = Integer.parseInt(category);
                products = productDAO.getProductsByCategory(catId);
            } else {
                products = productDAO.getAllProducts();
            }

            // Search filter
            if (keyword != null && !keyword.trim().isEmpty()) {
                List<Product> searchList = new ArrayList<>();
                for (Product p : products) {
                    if (p.getProductName().toLowerCase().contains(keyword.toLowerCase())) {
                        searchList.add(p);
                    }
                }
                products = searchList;
            }

            // Price filter
            if (min != null && max != null && !min.isEmpty() && !max.isEmpty()) {
                BigDecimal minPrice = new BigDecimal(min);
                BigDecimal maxPrice = new BigDecimal(max);

                List<Product> priceList = new ArrayList<>();
                for (Product p : products) {
                    if (p.getPrice().compareTo(minPrice) >= 0 &&
                        p.getPrice().compareTo(maxPrice) <= 0) {
                        priceList.add(p);
                    }
                }
                products = priceList;
            }

            // Sort
            if (sort != null) {
                if (sort.equals("low")) {
                    products.sort((p1, p2) -> p1.getPrice().compareTo(p2.getPrice()));
                } else if (sort.equals("high")) {
                    products.sort((p1, p2) -> p2.getPrice().compareTo(p1.getPrice()));
                }
            }

            // Pagination
            int totalProducts = products.size();
            int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

            if (page < 1) page = 1;
            if (page > totalPages && totalPages > 0) page = totalPages;

            int start = (page - 1) * pageSize;
            int end = Math.min(start + pageSize, totalProducts);

            List<Product> pageProducts = new ArrayList<>();
            if (start < totalProducts) {
                pageProducts = products.subList(start, end);
            }

            request.setAttribute("products", pageProducts);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);

            // keep filters for links
            request.setAttribute("keyword", keyword);
            request.setAttribute("category", category);
            request.setAttribute("min", min);
            request.setAttribute("max", max);
            request.setAttribute("sort", sort);

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("/views/pages/product.jsp")
               .forward(request, response);
    }
}