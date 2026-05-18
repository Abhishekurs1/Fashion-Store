package com.fashionstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.ProductSizeDAO;
import com.fashionstore.model.ProductSize;
import com.fashionstore.util.DBConnection;

public class ProductSizeDAOImpl implements ProductSizeDAO {

    @Override
    public boolean addProductSize(ProductSize size) {
        String sql = "INSERT INTO product_sizes (product_id, size_label, stock_quantity, sku_code, is_available) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, size.getProductId());
            ps.setString(2, size.getSizeLabel());
            ps.setInt(3, size.getStockQuantity());
            ps.setString(4, size.getSkuCode());
            ps.setBoolean(5, size.isAvailable());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ProductSize getSizeById(int productSizeId) {
        String sql = "SELECT * FROM product_sizes WHERE product_size_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, productSizeId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<ProductSize> getSizesByProductId(int productId) {
        List<ProductSize> list = new ArrayList<>();
        String sql = "SELECT * FROM product_sizes WHERE product_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, productId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public ProductSize getSizeByProductAndLabel(int productId, String sizeLabel) {
        String sql = "SELECT * FROM product_sizes WHERE product_id = ? AND size_label = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, productId);
            ps.setString(2, sizeLabel);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean isSizeAvailable(int productSizeId) {
        String sql = "SELECT is_available FROM product_sizes WHERE product_size_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, productSizeId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("is_available");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updateStock(int productSizeId, int quantity) {
        String sql = "UPDATE product_sizes SET stock_quantity = ? WHERE product_size_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setInt(2, productSizeId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean reduceStock(int productSizeId, int quantity) {
        String sql = "UPDATE product_sizes SET stock_quantity = stock_quantity - ? WHERE product_size_id = ? AND stock_quantity >= ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setInt(2, productSizeId);
            ps.setInt(3, quantity);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateProductSize(ProductSize size) {
        String sql = "UPDATE product_sizes SET size_label=?, stock_quantity=?, sku_code=?, is_available=? WHERE product_size_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, size.getSizeLabel());
            ps.setInt(2, size.getStockQuantity());
            ps.setString(3, size.getSkuCode());
            ps.setBoolean(4, size.isAvailable());
            ps.setInt(5, size.getProductSizeId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteProductSize(int productSizeId) {
        String sql = "DELETE FROM product_sizes WHERE product_size_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, productSizeId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private ProductSize map(ResultSet rs) throws Exception {
        ProductSize ps = new ProductSize();
        ps.setProductSizeId(rs.getInt("product_size_id"));
        ps.setProductId(rs.getInt("product_id"));
        ps.setSizeLabel(rs.getString("size_label"));
        ps.setStockQuantity(rs.getInt("stock_quantity"));
        ps.setSkuCode(rs.getString("sku_code"));
        ps.setAvailable(rs.getBoolean("is_available"));
        return ps;
    }
}