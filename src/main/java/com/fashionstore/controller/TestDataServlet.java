package com.fashionstore.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

import com.fashionstore.dao.CategoryDAO;
import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.ProductSizeDAO;
import com.fashionstore.dao.impl.CategoryDAOImpl;
import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.dao.impl.ProductSizeDAOImpl;
import com.fashionstore.model.Category;
import com.fashionstore.model.Product;
import com.fashionstore.model.ProductSize;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/test-data")
public class TestDataServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        CategoryDAO categoryDAO = new CategoryDAOImpl();
        ProductDAO productDAO = new ProductDAOImpl();
        ProductSizeDAO productSizeDAO = new ProductSizeDAOImpl();

        List<Category> categories = categoryDAO.getAllCategories();
        List<Product> products = productDAO.getAllProducts();

        out.println("<html><head><title>Test Data</title>");
        out.println("<style>");
        out.println("table{border-collapse:collapse;width:100%;margin-bottom:20px;}");
        out.println("th,td{border:1px solid #000;padding:8px;text-align:left;}");
        out.println("th{background:#f2f2f2;}");
        out.println("</style>");
        out.println("</head><body>");

        out.println("<h1>Database Select Query Test</h1>");

        out.println("<h2>Categories</h2>");
        out.println("<table>");
        out.println("<tr><th>ID</th><th>Name</th><th>Image</th><th>Created At</th></tr>");
        for (Category c : categories) {
            out.println("<tr>");
            out.println("<td>" + c.getCategoryId() + "</td>");
            out.println("<td>" + c.getCategoryName() + "</td>");
            out.println("<td>" + c.getCategoryImage() + "</td>");
            out.println("<td>" + c.getCreatedAt() + "</td>");
            out.println("</tr>");
        }
        out.println("</table>");

        out.println("<h2>Products</h2>");
        out.println("<table>");
        out.println("<tr><th>ID</th><th>Category ID</th><th>Name</th><th>Description</th><th>Price</th><th>Stock</th><th>Image</th><th>Status</th></tr>");
        for (Product p : products) {
            out.println("<tr>");
            out.println("<td>" + p.getProductId() + "</td>");
            out.println("<td>" + p.getCategoryId() + "</td>");
            out.println("<td>" + p.getProductName() + "</td>");
            out.println("<td>" + p.getDescription() + "</td>");
            out.println("<td>" + p.getPrice() + "</td>");
            out.println("<td>" + p.getStockQuantity() + "</td>");
            out.println("<td>" + p.getImageUrl() + "</td>");
            out.println("<td>" + p.getStatus() + "</td>");
            out.println("</tr>");

            List<ProductSize> sizes = productSizeDAO.getSizesByProductId(p.getProductId());

            out.println("<tr><td colspan='8'>");
            out.println("<b>Sizes for " + p.getProductName() + "</b>");
            out.println("<table style='width:100%;margin-top:10px;'>");
            out.println("<tr><th>Size ID</th><th>Product ID</th><th>Size Label</th><th>Stock</th><th>SKU</th><th>Available</th></tr>");

            for (ProductSize s : sizes) {
                out.println("<tr>");
                out.println("<td>" + s.getProductSizeId() + "</td>");
                out.println("<td>" + s.getProductId() + "</td>");
                out.println("<td>" + s.getSizeLabel() + "</td>");
                out.println("<td>" + s.getStockQuantity() + "</td>");
                out.println("<td>" + s.getSkuCode() + "</td>");
                out.println("<td>" + s.isAvailable() + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("</td></tr>");
        }
        out.println("</table>");

        out.println("</body></html>");
    }
}