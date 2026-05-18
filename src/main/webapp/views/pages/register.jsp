<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String error = request.getParameter("error");
    String success = request.getParameter("success");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register | Fashion Store</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/auth.css">
</head>
<body>

<div class="auth-page">
    <div class="auth-card">

        <div class="auth-brand">
            <h1>Fashion<span>Store</span></h1>
            <p>Create your account and start shopping with style.</p>
        </div>

        <div class="auth-form-box">
            <h2>Create Account</h2>
            <p class="auth-subtitle">Fill in your details to register.</p>

            <% if (error != null) { %>
                <div class="msg error-msg"><%= error %></div>
            <% } %>

            <% if (success != null) { %>
                <div class="msg success-msg"><%= success %></div>
            <% } %>

            <form action="${pageContext.request.contextPath}/register" method="post" class="auth-form">

                <div class="field-grid">
                    <input type="text" name="fullName" placeholder="Full Name" required>
                    <input type="email" name="email" placeholder="Email" required>
                </div>

                <div class="field-grid">
                    <input type="password" name="password" placeholder="Password" required>
                    <input type="text" name="phone" placeholder="Phone">
                </div>

                <input type="text" name="addressLine1" placeholder="Address Line 1">
                <input type="text" name="addressLine2" placeholder="Address Line 2">

                <div class="field-grid">
                    <input type="text" name="city" placeholder="City">
                    <input type="text" name="state" placeholder="State">
                </div>

                <div class="field-grid">
                    <input type="text" name="postalCode" placeholder="Postal Code">

                    <select name="gender">
                        <option value="">Select Gender</option>
                        <option value="Male">Male</option>
                        <option value="Female">Female</option>
                    </select>
                </div>

                <button type="submit" class="auth-btn">Register</button>
            </form>

            <p class="switch-link">
                Already have an account?
                <a href="${pageContext.request.contextPath}/views/pages/login.jsp">Login</a>
            </p>
        </div>

    </div>
</div>

</body>
</html>