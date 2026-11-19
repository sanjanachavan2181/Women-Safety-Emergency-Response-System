package com.womensafety.servlet;

import com.womensafety.dao.AlertDAO;
import com.womensafety.dao.ContactDAO;
import com.womensafety.dao.UserDAO;
import com.womensafety.model.Contact;
import com.womensafety.model.User;
import com.womensafety.util.NotificationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/user/sos")
public class SOSServlet extends HttpServlet {
    private final AlertDAO alertDao = new AlertDAO();
    private final ContactDAO contactDao = new ContactDAO();
    private final UserDAO userDao = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/sos.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int uid = (int) req.getSession().getAttribute("userId");

        // user must have confirmed in the browser
        if (!"yes".equals(req.getParameter("confirm"))) {
            req.setAttribute("error", "SOS request cancelled.");
            req.getRequestDispatcher("/WEB-INF/views/sos.jsp").forward(req, res);
            return;
        }

        Double lat = parseCoord(req.getParameter("latitude"), -90, 90);
        Double lng = parseCoord(req.getParameter("longitude"), -180, 180);
        if (lat == null || lng == null) { lat = null; lng = null; }   // both or none

        try {
            int alertId = alertDao.create(uid, lat, lng);          // status = Created
            List<Contact> contacts = contactDao.listByUser(uid);
            String status;

            if (contacts.isEmpty()) {
                status = "Saved - no emergency contacts to notify";
            } else {
                User u = userDao.findById(uid);
                boolean sent = NotificationService.send(u, contacts, lat, lng);
                status = sent ? "Sent" : "Pending - notification service not configured";
            }
            alertDao.updateStatus(alertId, status);

            req.setAttribute("alertStatus", status);
            req.setAttribute("hasLocation", lat != null);
            req.setAttribute("success", "Your SOS alert has been recorded.");
        } catch (SQLException e) {
            log("SOS failed", e);
            req.setAttribute("error", "Could not record the alert. Please call 112 immediately.");
        }
        req.getRequestDispatcher("/WEB-INF/views/sos.jsp").forward(req, res);
    }

    private Double parseCoord(String s, double min, double max) {
        try {
            if (s == null || s.isBlank()) return null;
            double d = Double.parseDouble(s);
            return (d >= min && d <= max) ? d : null;
        } catch (NumberFormatException e) { return null; }
    }
}
