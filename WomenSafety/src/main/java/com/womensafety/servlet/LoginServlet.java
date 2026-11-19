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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserDAO dao = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if ("1".equals(req.getParameter("registered"))) req.setAttribute("success", "Registration successful. Please log in.");
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String id = Validator.clean(req.getParameter("identifier")).toLowerCase();
        String password = req.getParameter("password");
        String error = "Invalid login details";

        try {
            User u = (Validator.isBlank(id) || password == null) ? null : dao.findByLogin(id);
            if (u != null && PasswordUtil.verify(password, u.getPasswordHash())) {
                HttpSession old = req.getSession(false);
                if (old != null) old.invalidate();            // prevent session fixation
                HttpSession s = req.getSession(true);
                s.setAttribute("userId", u.getUserId());
                s.setAttribute("userName", u.getName());
                s.setMaxInactiveInterval(30 * 60);
                res.sendRedirect(req.getContextPath() + "/user/dashboard");
                return;
            }
        } catch (SQLException e) {
            error = "Service temporarily unavailable. Please try again later.";
            log("Login failed", e);
        }
        req.setAttribute("error", error);
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, res);
    }
}
