package com.womensafety.dao;

import com.womensafety.model.Tip;
import com.womensafety.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipDAO {

    public List<Tip> listActive() throws SQLException {
        List<Tip> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection(); Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM safety_tips WHERE is_active=1 ORDER BY tip_id DESC")) {
            while (rs.next()) {
                Tip t = new Tip();
                t.setTipId(rs.getInt("tip_id"));
                t.setTitle(rs.getString("title"));
                t.setDescription(rs.getString("description"));
                list.add(t);
            }
        }
        return list;
    }

    public void add(String title, String description) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("INSERT INTO safety_tips(title,description) VALUES (?,?)")) {
            ps.setString(1, title);
            ps.setString(2, description);
            ps.executeUpdate();
        }
    }

    public void delete(int tipId) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM safety_tips WHERE tip_id=?")) {
            ps.setInt(1, tipId);
            ps.executeUpdate();
        }
    }
}
