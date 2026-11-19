package com.womensafety.servlet;

import com.womensafety.dao.TipDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

/** Public page - visitors do not need to log in. */
@WebServlet("/tips")
public class TipsServlet extends HttpServlet {
    private final TipDAO dao = new TipDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try { req.setAttribute("tips", dao.listActive()); }
        catch (SQLException e) { req.setAttribute("error", "Safety tips are temporarily unavailable."); log("tips", e); }
        req.getRequestDispatcher("/WEB-INF/views/tips.jsp").forward(req, res);
    }
}
