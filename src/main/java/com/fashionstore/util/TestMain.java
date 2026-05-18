package com.fashionstore.util;

import java.util.List;

import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.model.Product;

public class TestMain {

    public static void main(String[] args) {

        ProductDAO productDAO = new ProductDAOImpl();

        List<Product> products = productDAO.getAllProducts();

        for (Product p : products) {
            System.out.println(p.getProductId() + " - " + p.getProductName() + " - " + p.getPrice());
        }

        System.out.println("Test Completed ✅");
    }
}