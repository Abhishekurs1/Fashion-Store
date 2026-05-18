<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.fashionstore.model.User" %>

<%
    User profileUser = (User) request.getAttribute("profileUser");
    if (profileUser == null) {
        profileUser = (User) session.getAttribute("loggedUser");
    }

    String success = request.getParameter("success");
    String errorMessage = (String) request.getAttribute("errorMessage");
%>

<link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/profile.css?v=<%= System.currentTimeMillis() %>">

<%@ include file="../partials/navbar.jsp" %>

<div class="profile-page">
    <div class="profile-container">

        <div class="profile-header">
            <h2>My Profile</h2>
            <p>View and update your personal information</p>
        </div>

        <% if ("1".equals(success)) { %>
            <div class="alert success">Profile updated successfully.</div>
        <% } %>

        <% if (errorMessage != null) { %>
            <div class="alert error"><%= errorMessage %></div>
        <% } %>

        <div class="profile-grid">

            <div class="profile-card">
                <div class="profile-avatar">
                    <span><%= profileUser != null && profileUser.getFullName() != null && !profileUser.getFullName().isEmpty()
                            ? profileUser.getFullName().substring(0, 1).toUpperCase()
                            : "U" %></span>
                </div>

                <h3><%= profileUser != null ? profileUser.getFullName() : "" %></h3>
                <p><%= profileUser != null ? profileUser.getEmail() : "" %></p>

                <div class="profile-summary">
                    <div>
                        <label>Phone</label>
                        <span><%= profileUser != null ? profileUser.getPhone() : "" %></span>
                    </div>
                    <div>
                        <label>Gender</label>
                        <span><%= profileUser != null ? profileUser.getGender() : "" %></span>
                    </div>
                </div>

                <div class="profile-actions">
                    <a href="<%= request.getContextPath() %>/orders" class="secondary-btn">My Orders</a>
                    <a href="<%= request.getContextPath() %>/logout" class="secondary-btn">Logout</a>
                </div>
            </div>

            <div class="profile-form-card">
                <form action="<%= request.getContextPath() %>/profile" method="post" class="profile-form">

                    <div class="form-row">
                        <div class="form-group">
                            <label>Full Name</label>
                            <input type="text" name="fullName" value="<%= profileUser != null ? profileUser.getFullName() : "" %>" required>
                        </div>

                        <div class="form-group">
                            <label>Email</label>
                            <input type="email" value="<%= profileUser != null ? profileUser.getEmail() : "" %>" readonly>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label>Phone</label>
                            <input type="text" name="phone" value="<%= profileUser != null ? profileUser.getPhone() : "" %>" required>
                        </div>

                        <div class="form-group">
                            <label>Gender</label>
                            <select name="gender">
                                <option value="">Select</option>
                                <option value="Male" <%= profileUser != null && "Male".equalsIgnoreCase(profileUser.getGender()) ? "selected" : "" %>>Male</option>
                                <option value="Female" <%= profileUser != null && "Female".equalsIgnoreCase(profileUser.getGender()) ? "selected" : "" %>>Female</option>
                                <option value="Other" <%= profileUser != null && "Other".equalsIgnoreCase(profileUser.getGender()) ? "selected" : "" %>>Other</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label>Address Line 1</label>
                        <input type="text" name="addressLine1" value="<%= profileUser != null ? profileUser.getAddressLine1() : "" %>">
                    </div>

                    <div class="form-group">
                        <label>Address Line 2</label>
                        <input type="text" name="addressLine2" value="<%= profileUser != null ? profileUser.getAddressLine2() : "" %>">
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label>City</label>
                            <input type="text" name="city" value="<%= profileUser != null ? profileUser.getCity() : "" %>">
                        </div>

                        <div class="form-group">
                            <label>State</label>
                            <input type="text" name="state" value="<%= profileUser != null ? profileUser.getState() : "" %>">
                        </div>
                    </div>

                    <div class="form-group">
                        <label>Postal Code</label>
                        <input type="text" name="postalCode" value="<%= profileUser != null ? profileUser.getPostalCode() : "" %>">
                    </div>

                    <button type="submit" class="save-btn">Save Changes</button>
                </form>
            </div>

        </div>
    </div>
</div>

<%@ include file="../partials/footer.jsp" %>