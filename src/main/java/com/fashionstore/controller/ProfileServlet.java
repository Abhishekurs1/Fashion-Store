package com.fashionstore.controller;

import java.io.IOException;

import com.fashionstore.dao.UserDAO;
import com.fashionstore.dao.impl.UserDAOImpl;
import com.fashionstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loggedUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User sessionUser = (User) session.getAttribute("loggedUser");
        User freshUser = userDAO.getUserById(sessionUser.getUserId());

        request.setAttribute("profileUser", freshUser != null ? freshUser : sessionUser);
        request.getRequestDispatcher("/views/pages/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loggedUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User sessionUser = (User) session.getAttribute("loggedUser");

        User user = new User();
        user.setUserId(sessionUser.getUserId());
        user.setFullName(request.getParameter("fullName"));
        user.setPhone(request.getParameter("phone"));
        user.setAddressLine1(request.getParameter("addressLine1"));
        user.setAddressLine2(request.getParameter("addressLine2"));
        user.setCity(request.getParameter("city"));
        user.setState(request.getParameter("state"));
        user.setPostalCode(request.getParameter("postalCode"));
        user.setGender(request.getParameter("gender"));

        boolean updated = userDAO.updateUser(user);

        if (updated) {
            User refreshed = userDAO.getUserById(sessionUser.getUserId());
            session.setAttribute("loggedUser", refreshed != null ? refreshed : sessionUser);
            response.sendRedirect(request.getContextPath() + "/profile?success=1");
        } else {
            request.setAttribute("profileUser", sessionUser);
            request.setAttribute("errorMessage", "Profile update failed");
            request.getRequestDispatcher("/views/pages/profile.jsp").forward(request, response);
        }
    }
}