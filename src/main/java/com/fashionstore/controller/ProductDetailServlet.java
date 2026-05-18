package com.fashionstore.controller;

import java.io.IOException;
import java.util.List;

import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.ProductSizeDAO;
import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.dao.impl.ProductSizeDAOImpl;
import com.fashionstore.model.Product;
import com.fashionstore.model.ProductSize;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/product")
public class ProductDetailServlet extends HttpServlet {

    private ProductDAO productDAO;
    private ProductSizeDAO sizeDAO;

    @Override
    public void init() {
        productDAO = new ProductDAOImpl();
        sizeDAO = new ProductSizeDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int productId = Integer.parseInt(request.getParameter("id"));

            Product product = productDAO.getProductById(productId);

            if (product == null) {
                response.sendRedirect("products");
                return;
            }

            List<ProductSize> sizes = sizeDAO.getSizesByProductId(productId);

            // ✅ RELATED PRODUCTS
            List<Product> relatedProducts =
                    productDAO.getRelatedProducts(product.getCategoryId(), productId);

            request.setAttribute("product", product);
            request.setAttribute("sizes", sizes);
            request.setAttribute("relatedProducts", relatedProducts);

            request.getRequestDispatcher("/views/pages/product-detail.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("products");
        }
    }
}