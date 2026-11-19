package com.womensafety.servlet;

import com.womensafety.dao.ContactDAO;
import com.womensafety.model.Contact;
import com.womensafety.util.Validator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/user/contacts")
public class ContactServlet extends HttpServlet {
    private final ContactDAO dao = new ContactDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int uid = (int) req.getSession().getAttribute("userId");
        try {
            String edit = req.getParameter("edit");
            if (edit != null) req.setAttribute("editContact", dao.get(Integer.parseInt(edit), uid));
            req.setAttribute("contacts", dao.listByUser(uid));
        } catch (NumberFormatException e) {
            req.setAttribute("error", "Invalid contact.");
        } catch (SQLException e) {
            req.setAttribute("error", "Could not load contacts."); log("contacts", e);
        }
        req.getRequestDispatcher("/WEB-INF/views/contacts.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int uid = (int) req.getSession().getAttribute("userId");
        String action = Validator.clean(req.getParameter("action"));
        String msg = null, err = null;

        try {
            if ("delete".equals(action)) {
                msg = dao.delete(Integer.parseInt(req.getParameter("contactId")), uid) ? "Contact deleted." : "Contact not found.";
            } else if ("add".equals(action) || "edit".equals(action)) {
                Contact c = new Contact();
                c.setUserId(uid);
                c.setContactName(Validator.clean(req.getParameter("contactName")));
                c.setMobile(Validator.clean(req.getParameter("mobile")));
                c.setRelation(Validator.clean(req.getParameter("relation")));

                if (Validator.isBlank(c.getContactName()) || c.getContactName().length() > 100) err = "Please enter the contact name.";
                else if (!Validator.validMobile(c.getMobile())) err = "Contact mobile must be 10 digits.";
                else if ("add".equals(action)) { dao.add(c); msg = "Contact added."; }
                else {
                    c.setContactId(Integer.parseInt(req.getParameter("contactId")));
                    msg = dao.update(c) ? "Contact updated." : "Contact not found.";
                }
            } else err = "Unknown action.";
        } catch (NumberFormatException e) { err = "Invalid contact.";
        } catch (SQLException e) { err = "Operation failed. Please try again."; log("contact op", e); }

        // Post/Redirect/Get with flash messages in session
        HttpSession s = req.getSession();
        if (msg != null) s.setAttribute("flashSuccess", msg);
        if (err != null) s.setAttribute("flashError", err);
        res.sendRedirect(req.getContextPath() + "/user/contacts");
    }
}
