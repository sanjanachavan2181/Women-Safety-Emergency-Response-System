package com.womensafety.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;

/** Protects /user/* (needs userId in session) and /admin/* (needs adminId in session). */
@WebFilter(urlPatterns = {"/user/*", "/admin/*"})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest rq, ServletResponse rs, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) rq;
        HttpServletResponse res = (HttpServletResponse) rs;
        String path = req.getServletPath();
        HttpSession session = req.getSession(false);
        String ctx = req.getContextPath();

        if (path.equals("/admin/login") || path.equals("/admin/logout")) { chain.doFilter(rq, rs); return; }

        boolean isAdminArea = path.startsWith("/admin");
        String key = isAdminArea ? "adminId" : "userId";
        boolean ok = session != null && session.getAttribute(key) != null;

        if (!ok) {
            res.sendRedirect(ctx + (isAdminArea ? "/admin/login" : "/login"));
            return;
        }
        // stop browsers showing protected pages from cache after logout
        res.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        res.setHeader("Pragma", "no-cache");
        chain.doFilter(rq, rs);
    }
}
