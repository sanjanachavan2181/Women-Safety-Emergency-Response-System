package com.womensafety.servlet;

import com.womensafety.dao.AdminDAO;
import com.womensafety.util.PasswordUtil;
import com.womensafety.util.Validator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/login")
public class AdminLoginServlet extends HttpServlet {
    private final AdminDAO dao = new AdminDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/admin-login.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String username = Validator.clean(req.getParameter("username"));
        String password = req.getParameter("password");
        String error = "Access denied: invalid admin credentials";
        try {
            String[] a = (Validator.isBlank(username) || password == null) ? null : dao.findByUsername(username);
            if (a != null && PasswordUtil.verify(password, a[1])) {
                HttpSession old = req.getSession(false);
                if (old != null) old.invalidate();
                HttpSession s = req.getSession(true);
                s.setAttribute("adminId", Integer.parseInt(a[0]));
                s.setAttribute("adminName", username);
                s.setMaxInactiveInterval(20 * 60);
                dao.audit(Integer.parseInt(a[0]), "LOGIN", "Admin logged in");
                res.sendRedirect(req.getContextPath() + "/admin/dashboard");
                return;
            }
        } catch (SQLException e) { error = "Service temporarily unavailable."; log("admin login", e); }
        req.setAttribute("error", error);
        req.getRequestDispatcher("/WEB-INF/views/admin-login.jsp").forward(req, res);
    }
}
