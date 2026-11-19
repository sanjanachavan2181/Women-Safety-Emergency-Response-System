package com.womensafety.servlet;

import com.womensafety.dao.AlertDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/user/history")
public class AlertHistoryServlet extends HttpServlet {
    private final AlertDAO dao = new AlertDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int uid = (int) req.getSession().getAttribute("userId");
        try { req.setAttribute("alerts", dao.listByUser(uid)); }
        catch (SQLException e) { req.setAttribute("error", "Could not load alert history."); log("history", e); }
        req.getRequestDispatcher("/WEB-INF/views/history.jsp").forward(req, res);
    }
}
