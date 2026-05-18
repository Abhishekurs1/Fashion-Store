package com.fashionstore.dao;

import java.util.List;
import com.fashionstore.model.Category;

public interface CategoryDAO {

    // Create
    boolean addCategory(Category category);

    // Read
    List<Category> getAllCategories();
    Category getCategoryById(int categoryId);
    Category getCategoryByName(String categoryName);

    // Update
    boolean updateCategory(Category category);

    // Delete
    boolean deleteCategory(int categoryId);

    // Utility
    boolean isCategoryExists(String categoryName);
}