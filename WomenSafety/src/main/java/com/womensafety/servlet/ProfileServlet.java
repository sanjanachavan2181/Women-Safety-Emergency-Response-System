package com.womensafety.servlet;

import com.womensafety.dao.UserDAO;
import com.womensafety.model.User;
import com.womensafety.util.Validator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/user/profile")
public class ProfileServlet extends HttpServlet {
    private final UserDAO dao = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int uid = (int) req.getSession().getAttribute("userId");
        try { req.setAttribute("user", dao.findById(uid)); }
        catch (SQLException e) { req.setAttribute("error", "Could not load profile."); log("profile", e); }
        req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int uid = (int) session.getAttribute("userId");
        User u = new User();
        u.setUserId(uid);
        u.setName(Validator.clean(req.getParameter("name")));
        u.setEmail(Validator.clean(req.getParameter("email")).toLowerCase());
        u.setMobile(Validator.clean(req.getParameter("mobile")));

        String error = null;
        if (Validator.isBlank(u.getName()) || u.getName().length() > 100) error = "Please enter a valid name.";
        else if (!Validator.validEmail(u.getEmail())) error = "Please enter a valid email address.";
        else if (!Validator.validMobile(u.getMobile())) error = "Mobile number must be 10 digits.";

        try {
            if (error == null && dao.exists(u.getEmail(), u.getMobile(), uid)) error = "Email or mobile is already used by another account.";
            if (error == null) {
                dao.updateProfile(u);
                session.setAttribute("userName", u.getName());
                req.setAttribute("success", "Profile updated successfully.");
            }
        } catch (SQLException e) { error = "Update failed. Please try again."; log("profile update", e); }

        req.setAttribute("error", error);
        req.setAttribute("user", u);
        req.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, res);
    }
}
