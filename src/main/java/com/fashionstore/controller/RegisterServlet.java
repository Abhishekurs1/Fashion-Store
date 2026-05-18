package com.fashionstore.controller;

import java.io.IOException;

import com.fashionstore.dao.UserDAO;
import com.fashionstore.dao.impl.UserDAOImpl;
import com.fashionstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAOImpl();

    // ✅ HANDLE GET (fixes 405 when opening /register)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // redirect to register page
        response.sendRedirect(request.getContextPath() + "/views/pages/register.jsp");
    }

    // ✅ HANDLE POST (form submit)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String email = request.getParameter("email");

            // 🔴 CHECK DUPLICATE EMAIL
            if (userDAO.isEmailExists(email)) {
                response.sendRedirect(request.getContextPath() + "/views/pages/register.jsp?error=Email already exists");
                return;
            }

            User user = new User();

            user.setFullName(request.getParameter("fullName"));
            user.setEmail(email);
            user.setPassword(request.getParameter("password"));
            user.setPhone(request.getParameter("phone"));

            user.setAddressLine1(request.getParameter("addressLine1"));
            user.setAddressLine2(request.getParameter("addressLine2"));
            user.setCity(request.getParameter("city"));
            user.setState(request.getParameter("state"));
            user.setPostalCode(request.getParameter("postalCode"));

            user.setGender(request.getParameter("gender"));
            user.setActive(true);

            boolean success = userDAO.registerUser(user);

            if (success) {
                // ✅ FIX: redirect to servlet, NOT JSP
                response.sendRedirect(request.getContextPath() + "/login?success=Registered");
            } else {
                response.sendRedirect(request.getContextPath() + "/views/pages/register.jsp?error=Failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/views/pages/register.jsp?error=Error");
        }
    }
}