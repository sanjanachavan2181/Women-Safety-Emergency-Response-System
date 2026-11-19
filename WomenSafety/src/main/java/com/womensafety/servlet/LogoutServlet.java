package com.womensafety.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet({"/logout", "/admin/logout"})
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpSession s = req.getSession(false);
        if (s != null) s.invalidate();
        boolean admin = req.getServletPath().startsWith("/admin");
        res.sendRedirect(req.getContextPath() + (admin ? "/admin/login" : "/login"));
    }
}
