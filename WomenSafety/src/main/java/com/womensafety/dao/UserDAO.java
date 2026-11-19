package com.womensafety.dao;

import com.womensafety.model.User;
import com.womensafety.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private User map(ResultSet rs) throws SQLException {
        User u = new User();
        u.setUserId(rs.getInt("user_id"));
        u.setName(rs.getString("name"));
        u.setEmail(rs.getString("email"));
        u.setMobile(rs.getString("mobile"));
        u.setPasswordHash(rs.getString("password_hash"));
        return u;
    }

    public boolean exists(String email, String mobile) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE email=? OR mobile=?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, mobile);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        }
    }

    public boolean exists(String email, String mobile, int excludeUserId) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE (email=? OR mobile=?) AND user_id<>?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, mobile);
            ps.setInt(3, excludeUserId);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        }
    }

    public void register(User u) throws SQLException {
        String sql = "INSERT INTO users(name,email,mobile,password_hash) VALUES (?,?,?,?)";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, u.getName());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getMobile());
            ps.setString(4, u.getPasswordHash());
            ps.executeUpdate();
        }
    }

    /** Login by email OR mobile. */
    public User findByLogin(String emailOrMobile) throws SQLException {
        String sql = "SELECT * FROM users WHERE email=? OR mobile=?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, emailOrMobile);
            ps.setString(2, emailOrMobile);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
        }
    }

    public User findById(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_id=?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
        }
    }

    public void updateProfile(User u) throws SQLException {
        String sql = "UPDATE users SET name=?, email=?, mobile=? WHERE user_id=?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, u.getName());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getMobile());
            ps.setInt(4, u.getUserId());
            ps.executeUpdate();
        }
    }

    public List<User> listAll() throws SQLException {
        List<User> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection(); Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM users ORDER BY user_id")) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public void delete(int userId) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM users WHERE user_id=?")) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }
}
