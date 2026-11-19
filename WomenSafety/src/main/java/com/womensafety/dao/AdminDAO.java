package com.womensafety.dao;

import com.womensafety.util.DBConnection;
import java.sql.*;

public class AdminDAO {

    /** Returns {admin_id, password_hash} or null if username not found. */
    public String[] findByUsername(String username) throws SQLException {
        String sql = "SELECT admin_id, password_hash FROM admins WHERE username=?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? new String[]{String.valueOf(rs.getInt(1)), rs.getString(2)} : null;
            }
        }
    }

    public void audit(int adminId, String action, String details) throws SQLException {
        String sql = "INSERT INTO admin_audit(admin_id,action,details) VALUES (?,?,?)";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, adminId);
            ps.setString(2, action);
            ps.setString(3, details);
            ps.executeUpdate();
        }
    }
}
