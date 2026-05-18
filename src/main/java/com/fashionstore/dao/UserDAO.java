package com.fashionstore.dao;

import java.util.List;
import com.fashionstore.model.User;

public interface UserDAO {

    // 🔐 AUTHENTICATION
    boolean registerUser(User user);
    User loginUser(String email, String password);

    // 🔍 FETCH
    User getUserById(int userId);
    User getUserByEmail(String email);

    // ✏️ UPDATE
    boolean updateUser(User user);
    boolean updatePassword(int userId, String newPassword);
    boolean updateLastLogin(int userId);
    boolean updateUserStatus(int userId, boolean isActive);

    // ❌ DELETE
    boolean deleteUser(int userId);

    // 🔎 VALIDATION
    boolean isEmailExists(String email);

    // 📋 ADMIN / DEBUG
    List<User> getAllUsers();
}