package com.fashionstore.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fashionstore.util.DBConnection;

@WebServlet("/db-test")
public class DBTestServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<h1>DB Connection Test</h1>");

        try {
            Connection conn = DBConnection.getConnection();

            if (conn != null && !conn.isClosed()) {
                out.println("<h2 style='color:green;'>✅ Connection Successful</h2>");
                conn.close();
            } else {
                out.println("<h2 style='color:red;'>❌ Connection Failed</h2>");
            }

        } catch (Exception e) {
            out.println("<h2 style='color:red;'>❌ Error Occurred</h2>");
            e.printStackTrace(out);
        }
    }
}
