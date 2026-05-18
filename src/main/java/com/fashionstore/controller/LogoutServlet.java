package com.fashionstore.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 🔥 Get session (if exists)
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();   // ✅ destroy session
        }

        // 🔥 Redirect to login page
        response.sendRedirect(request.getContextPath() + "/login");
    }
}