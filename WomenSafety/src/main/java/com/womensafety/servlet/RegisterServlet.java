package com.womensafety.servlet;

import com.womensafety.dao.UserDAO;
import com.womensafety.model.User;
import com.womensafety.util.PasswordUtil;
import com.womensafety.util.Validator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final UserDAO dao = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String name = Validator.clean(req.getParameter("name"));
        String email = Validator.clean(req.getParameter("email")).toLowerCase();
        String mobile = Validator.clean(req.getParameter("mobile"));
        String password = req.getParameter("password");
        String confirm = req.getParameter("confirm");

        String error = null;
        if (Validator.isBlank(name) || name.length() > 100) error = "Please enter a valid name.";
        else if (!Validator.validEmail(email)) error = "Please enter a valid email address.";
        else if (!Validator.validMobile(mobile)) error = "Mobile number must be 10 digits.";
        else if (!Validator.validPassword(password)) error = "Password must be at least 8 characters.";
        else if (!password.equals(confirm)) error = "Passwords do not match.";

        try {
            if (error == null && dao.exists(email, mobile)) error = "Account already registered with this email or mobile.";
            if (error == null) {
                User u = new User();
                u.setName(name); u.setEmail(email); u.setMobile(mobile);
                u.setPasswordHash(PasswordUtil.hash(password));
                dao.register(u);
                res.sendRedirect(req.getContextPath() + "/login?registered=1");
                return;
            }
        } catch (SQLException e) {
            error = "Service temporarily unavailable. Please try again later.";
            log("Registration failed", e);
        }
        req.setAttribute("error", error);
        req.setAttribute("name", name); req.setAttribute("email", email); req.setAttribute("mobile", mobile);
        req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, res);
    }
}
