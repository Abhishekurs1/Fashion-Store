<%@ page contentType="text/html;charset=UTF-8" %>

<%
    String success = request.getParameter("success");
    String error = request.getParameter("error");
%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/auth.css">

<div class="auth-page">

    <div class="auth-card">

        <!-- LEFT SIDE BRAND -->
        <div class="auth-brand">
            <h1>Fashion<span>Store</span></h1>
            <p>Welcome back! Login to continue shopping.</p>
        </div>

        <!-- RIGHT SIDE FORM -->
        <div class="auth-form-box">

            <h2>Login</h2>
            <p class="auth-subtitle">Enter your credentials</p>

            <% if (success != null) { %>
                <div class="msg success-msg">
                    Registration successful! Please login.
                </div>
            <% } %>

            <% if (error != null) { %>
                <div class="msg error-msg">
                    Invalid email or password
                </div>
            <% } %>

            <form action="${pageContext.request.contextPath}/login" method="post" class="auth-form">

                <input type="email" name="email" placeholder="Email" required>

                <input type="password" name="password" placeholder="Password" required>

                <button type="submit" class="auth-btn">Login</button>

            </form>

            <p class="switch-link">
                Don’t have an account?
                <a href="${pageContext.request.contextPath}/views/pages/register.jsp">Register</a>
            </p>

        </div>

    </div>

</div>