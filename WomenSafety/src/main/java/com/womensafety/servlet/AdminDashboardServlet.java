package com.womensafety.servlet;

import com.womensafety.dao.AdminDAO;
import com.womensafety.dao.TipDAO;
import com.womensafety.dao.UserDAO;
import com.womensafety.util.Validator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

/** Admin: list users, delete users, publish / remove safety tips. Every change is audited. */
@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
    private final UserDAO userDao = new UserDAO();
    private final TipDAO tipDao = new TipDAO();
    private final AdminDAO adminDao = new AdminDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            req.setAttribute("users", userDao.listAll());
            req.setAttribute("tips", tipDao.listActive());
        } catch (SQLException e) { req.setAttribute("error", "Could not load data."); log("admin", e); }
        req.getRequestDispatcher("/WEB-INF/views/admin-dashboard.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession s = req.getSession();
        int adminId = (int) s.getAttribute("adminId");
        String action = Validator.clean(req.getParameter("action"));
        try {
            switch (action) {
                case "addTip": {
                    String t = Validator.clean(req.getParameter("title"));
                    String d = Validator.clean(req.getParameter("description"));
                    if (Validator.isBlank(t) || Validator.isBlank(d)) { s.setAttribute("flashError", "Title and description are required."); break; }
                    tipDao.add(t, d);
                    adminDao.audit(adminId, "ADD_TIP", t);
                    s.setAttribute("flashSuccess", "Safety tip published.");
                    break;
                }
                case "deleteTip": {
                    int id = Integer.parseInt(req.getParameter("id"));
                    tipDao.delete(id);
                    adminDao.audit(adminId, "DELETE_TIP", "tip_id=" + id);
                    s.setAttribute("flashSuccess", "Tip removed.");
                    break;
                }
                case "deleteUser": {
                    int id = Integer.parseInt(req.getParameter("id"));
                    userDao.delete(id);
                    adminDao.audit(adminId, "DELETE_USER", "user_id=" + id);
                    s.setAttribute("flashSuccess", "User removed.");
                    break;
                }
                default: s.setAttribute("flashError", "Unknown action.");
            }
        } catch (NumberFormatException e) { s.setAttribute("flashError", "Invalid id.");
        } catch (SQLException e) { s.setAttribute("flashError", "Operation failed."); log("admin op", e); }
        res.sendRedirect(req.getContextPath() + "/admin/dashboard");
    }
}
