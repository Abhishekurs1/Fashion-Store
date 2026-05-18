package com.fashionstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.CartItemDAO;
import com.fashionstore.model.CartItem;
import com.fashionstore.util.DBConnection;

public class CartItemDAOImpl implements CartItemDAO {

    @Override
    public boolean addToCart(CartItem item) {
        String sql = "INSERT INTO cart_items (cart_id, product_id, product_size_id, quantity, price_at_time) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, item.getCartId());
            ps.setInt(2, item.getProductId());
            ps.setInt(3, item.getProductSizeId());
            ps.setInt(4, item.getQuantity());
            ps.setBigDecimal(5, item.getPriceAtTime());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🔥 IMPORTANT METHOD (FIXED + SAFE)
    @Override
    public List<CartItem> getCartItems(int cartId) {

        List<CartItem> list = new ArrayList<>();

        String sql =
                "SELECT ci.*, " +
                "p.product_name, " +
                "ps.size_label " +
                "FROM cart_items ci " +
                "INNER JOIN products p ON ci.product_id = p.product_id " +
                "INNER JOIN product_sizes ps ON ci.product_size_id = ps.product_size_id " +
                "WHERE ci.cart_id = ? " +
                "ORDER BY ci.created_at DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cartId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    CartItem item = new CartItem();

                    item.setCartItemId(rs.getInt("cart_item_id"));
                    item.setCartId(rs.getInt("cart_id"));
                    item.setProductId(rs.getInt("product_id"));
                    item.setProductSizeId(rs.getInt("product_size_id"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setPriceAtTime(rs.getBigDecimal("price_at_time"));
                    item.setCreatedAt(rs.getTimestamp("created_at"));

                    // 🔥 CRITICAL (for checkout + order)
                    item.setProductName(rs.getString("product_name"));
                    item.setSizeLabel(rs.getString("size_label"));

                    list.add(item);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public CartItem getCartItemById(int cartItemId) {
        String sql = "SELECT * FROM cart_items WHERE cart_item_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cartItemId);

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
    public CartItem getCartItem(int cartId, int productId, int productSizeId) {
        String sql = "SELECT * FROM cart_items WHERE cart_id=? AND product_id=? AND product_size_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cartId);
            ps.setInt(2, productId);
            ps.setInt(3, productSizeId);

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
    public boolean updateCartItem(CartItem item) {
        String sql = "UPDATE cart_items SET quantity=? WHERE cart_item_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, item.getQuantity());
            ps.setInt(2, item.getCartItemId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateQuantity(int cartItemId, int quantity) {
        String sql = "UPDATE cart_items SET quantity=? WHERE cart_item_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setInt(2, cartItemId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeCartItem(int cartItemId) {
        String sql = "DELETE FROM cart_items WHERE cart_item_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cartItemId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean clearCart(int cartId) {
        String sql = "DELETE FROM cart_items WHERE cart_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cartId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isProductInCart(int cartId, int productId, int productSizeId) {
        String sql = "SELECT 1 FROM cart_items WHERE cart_id=? AND product_id=? AND product_size_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cartId);
            ps.setInt(2, productId);
            ps.setInt(3, productSizeId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private CartItem map(ResultSet rs) throws Exception {
        CartItem item = new CartItem();
        item.setCartItemId(rs.getInt("cart_item_id"));
        item.setCartId(rs.getInt("cart_id"));
        item.setProductId(rs.getInt("product_id"));
        item.setProductSizeId(rs.getInt("product_size_id"));
        item.setQuantity(rs.getInt("quantity"));
        item.setPriceAtTime(rs.getBigDecimal("price_at_time"));
        item.setCreatedAt(rs.getTimestamp("created_at"));
        return item;
    }
}