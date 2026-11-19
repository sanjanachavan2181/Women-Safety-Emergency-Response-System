package com.womensafety.dao;

import com.womensafety.model.Contact;
import com.womensafety.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Every query includes user_id so a user can only touch their own contacts. */
public class ContactDAO {

    private Contact map(ResultSet rs) throws SQLException {
        Contact c = new Contact();
        c.setContactId(rs.getInt("contact_id"));
        c.setUserId(rs.getInt("user_id"));
        c.setContactName(rs.getString("contact_name"));
        c.setMobile(rs.getString("mobile"));
        c.setRelation(rs.getString("relation"));
        return c;
    }

    public List<Contact> listByUser(int userId) throws SQLException {
        List<Contact> list = new ArrayList<>();
        String sql = "SELECT * FROM emergency_contacts WHERE user_id=? ORDER BY contact_name";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(map(rs)); }
        }
        return list;
    }

    public Contact get(int contactId, int userId) throws SQLException {
        String sql = "SELECT * FROM emergency_contacts WHERE contact_id=? AND user_id=?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, contactId);
            ps.setInt(2, userId);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
        }
    }

    public void add(Contact ct) throws SQLException {
        String sql = "INSERT INTO emergency_contacts(user_id,contact_name,mobile,relation) VALUES (?,?,?,?)";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, ct.getUserId());
            ps.setString(2, ct.getContactName());
            ps.setString(3, ct.getMobile());
            ps.setString(4, ct.getRelation());
            ps.executeUpdate();
        }
    }

    public boolean update(Contact ct) throws SQLException {
        String sql = "UPDATE emergency_contacts SET contact_name=?, mobile=?, relation=? WHERE contact_id=? AND user_id=?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, ct.getContactName());
            ps.setString(2, ct.getMobile());
            ps.setString(3, ct.getRelation());
            ps.setInt(4, ct.getContactId());
            ps.setInt(5, ct.getUserId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int contactId, int userId) throws SQLException {
        String sql = "DELETE FROM emergency_contacts WHERE contact_id=? AND user_id=?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, contactId);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        }
    }
}
