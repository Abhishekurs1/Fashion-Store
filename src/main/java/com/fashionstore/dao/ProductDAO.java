package com.fashionstore.dao;

import java.util.List;
import com.fashionstore.model.Product;

public interface ProductDAO {

    // Create
    boolean addProduct(Product product);

    // Read
    Product getProductById(int productId);
    List<Product> getAllProducts();

    // Filtering
    List<Product> getProductsByCategory(int categoryId);
    List<Product> getProductsByStatus(String status);

    // Search
    List<Product> searchProducts(String keyword);

    // Advanced filtering (optional but useful)
    List<Product> getProductsByPriceRange(double minPrice, double maxPrice);

    // Update
    boolean updateProduct(Product product);
    boolean updateProductStatus(int productId, String status);

    // Delete
    boolean deleteProduct(int productId);
    
    List<Product> getRelatedProducts(int categoryId, int productId);
}