package com.fashionstore.model;

import java.sql.Timestamp;

public class Category {

    private int categoryId;
    private String categoryName;
    private String categoryImage;
    private Timestamp createdAt;

    // Default Constructor
    public Category() {}

    // Parameterized Constructor
    public Category(int categoryId, String categoryName, String categoryImage, Timestamp createdAt) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
        this.createdAt = createdAt;
    }

    // Getters and Setters

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}