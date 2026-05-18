package com.fashionstore.dao;

import java.util.List;
import com.fashionstore.model.ProductSize;

public interface ProductSizeDAO {

    // Create
    boolean addProductSize(ProductSize size);

    // Read
    ProductSize getSizeById(int productSizeId);
    List<ProductSize> getSizesByProductId(int productId);

    // Utility
    ProductSize getSizeByProductAndLabel(int productId, String sizeLabel);
    boolean isSizeAvailable(int productSizeId);

    // Stock management
    boolean updateStock(int productSizeId, int quantity);
    boolean reduceStock(int productSizeId, int quantity);

    // Update
    boolean updateProductSize(ProductSize size);

    // Delete
    boolean deleteProductSize(int productSizeId);
}