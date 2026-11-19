package com.womensafety.dao;

import com.womensafety.model.Alert;
import com.womensafety.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlertDAO {

    /** Creates an alert with status "Created" and returns its generated id. */
    public int create(int userId, Double lat, Double lng) throws SQLException {
        String sql = "INSERT INTO sos_alerts(user_id,latitude,longitude,status) VALUES (?,?,?,'Created')";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            if (lat == null || lng == null) { ps.setNull(2, Types.DOUBLE); ps.setNull(3, Types.DOUBLE); }
            else { ps.setDouble(2, lat); ps.setDouble(3, lng); }
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { return rs.next() ? rs.getInt(1) : -1; }
        }
    }

    public void updateStatus(int alertId, String status) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("UPDATE sos_alerts SET status=? WHERE alert_id=?")) {
            ps.setString(1, status);
            ps.setInt(2, alertId);
            ps.executeUpdate();
        }
    }

    /** Only the owner's alerts, newest first. */
    public List<Alert> listByUser(int userId) throws SQLException {
        List<Alert> list = new ArrayList<>();
        String sql = "SELECT * FROM sos_alerts WHERE user_id=? ORDER BY alert_time DESC, alert_id DESC";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Alert a = new Alert();
                    a.setAlertId(rs.getInt("alert_id"));
                    a.setUserId(rs.getInt("user_id"));
                    a.setAlertTime(rs.getTimestamp("alert_time"));
                    double la = rs.getDouble("latitude");  boolean laNull = rs.wasNull();
                    double lo = rs.getDouble("longitude"); boolean loNull = rs.wasNull();
                    a.setLatitude(laNull ? null : la);
                    a.setLongitude(loNull ? null : lo);
                    a.setStatus(rs.getString("status"));
                    list.add(a);
                }
            }
        }
        return list;
    }
}
