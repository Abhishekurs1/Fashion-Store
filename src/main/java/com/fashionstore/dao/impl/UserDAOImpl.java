package com.fashionstore.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.UserDAO;
import com.fashionstore.model.User;
import com.fashionstore.util.DBConnection;

public class UserDAOImpl implements UserDAO {

    // 🔐 REGISTER
    @Override
    public boolean registerUser(User user) {

        String sql = "INSERT INTO users "
                + "(full_name, email, password, phone, address_line1, address_line2, city, state, postal_code, gender, is_active) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());

            ps.setString(5, user.getAddressLine1());
            ps.setString(6, user.getAddressLine2() != null ? user.getAddressLine2() : "");
            ps.setString(7, user.getCity());
            ps.setString(8, user.getState());
            ps.setString(9, user.getPostalCode());

            ps.setString(10, user.getGender() != null ? user.getGender() : "");
            ps.setBoolean(11, true); // always active

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace(); // IMPORTANT for debugging
        }

        return false;
    }

    // 🔐 LOGIN
    @Override
    public User loginUser(String email, String password) {

        String sql = "SELECT * FROM users WHERE email=? AND password=? AND is_active=1";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = map(rs);
                updateLastLogin(user.getUserId());
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 🔍 GET BY ID
    @Override
    public User getUserById(int userId) {

        String sql = "SELECT * FROM users WHERE user_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return map(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 🔍 GET BY EMAIL
    @Override
    public User getUserByEmail(String email) {

        String sql = "SELECT * FROM users WHERE email=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return map(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ✏️ UPDATE USER
    @Override
    public boolean updateUser(User user) {

        String sql = "UPDATE users SET full_name=?, phone=?, address_line1=?, address_line2=?, city=?, state=?, postal_code=?, gender=? WHERE user_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getPhone());
            ps.setString(3, user.getAddressLine1());
            ps.setString(4, user.getAddressLine2());
            ps.setString(5, user.getCity());
            ps.setString(6, user.getState());
            ps.setString(7, user.getPostalCode());
            ps.setString(8, user.getGender());
            ps.setInt(9, user.getUserId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 🔑 UPDATE PASSWORD
    @Override
    public boolean updatePassword(int userId, String newPassword) {

        String sql = "UPDATE users SET password=? WHERE user_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newPassword);
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 🔄 LAST LOGIN
    @Override
    public boolean updateLastLogin(int userId) {

        String sql = "UPDATE users SET last_login=NOW() WHERE user_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 🔒 USER STATUS
    @Override
    public boolean updateUserStatus(int userId, boolean isActive) {

        String sql = "UPDATE users SET is_active=? WHERE user_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBoolean(1, isActive);
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ❌ DELETE
    @Override
    public boolean deleteUser(int userId) {

        String sql = "DELETE FROM users WHERE user_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 🔎 EMAIL CHECK
    @Override
    public boolean isEmailExists(String email) {

        String sql = "SELECT 1 FROM users WHERE email=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 📋 ALL USERS
    @Override
    public List<User> getAllUsers() {

        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 🔁 MAPPING METHOD
    private User map(ResultSet rs) throws Exception {

        User u = new User();

        u.setUserId(rs.getInt("user_id"));
        u.setFullName(rs.getString("full_name"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        u.setPhone(rs.getString("phone"));

        u.setAddressLine1(rs.getString("address_line1"));
        u.setAddressLine2(rs.getString("address_line2"));
        u.setCity(rs.getString("city"));
        u.setState(rs.getString("state"));
        u.setPostalCode(rs.getString("postal_code"));

        u.setGender(rs.getString("gender"));
        u.setCreatedAt(rs.getTimestamp("created_at"));
        u.setUpdatedAt(rs.getTimestamp("updated_at"));
        u.setLastLogin(rs.getTimestamp("last_login"));
        u.setActive(rs.getBoolean("is_active"));

        return u;
    }
}