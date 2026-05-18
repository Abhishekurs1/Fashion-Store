package com.fashionstore.controller;

import java.io.IOException;

import com.fashionstore.dao.UserDAO;
import com.fashionstore.dao.impl.UserDAOImpl;
import com.fashionstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAOImpl();

    // 🔹 SHOW LOGIN PAGE
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/views/pages/login.jsp")
               .forward(request, response);
    }

    // 🔹 HANDLE LOGIN
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            // 🔐 CHECK USER IN DB
            User user = userDAO.loginUser(email, password);

            if (user != null) {

                // ✅ CREATE SESSION
                HttpSession session = request.getSession();
                session.setAttribute("loggedUser", user);

                // 🔁 REDIRECT TO HOME
                response.sendRedirect(request.getContextPath() + "/home");

            } else {
                // ❌ LOGIN FAILED
                response.sendRedirect(request.getContextPath() + "/login?error=Invalid");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/login?error=Something went wrong");
        }
    }
}