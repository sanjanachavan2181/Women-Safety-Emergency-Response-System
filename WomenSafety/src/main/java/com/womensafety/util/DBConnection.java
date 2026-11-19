package com.womensafety.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** JDBC connection helper. Change USER / PASSWORD to match your MySQL setup. */
public class DBConnection {
   private static final String URL =
    System.getenv("DB_URL");

private static final String USER =
    System.getenv("DB_USER");

private static final String PASSWORD =
    System.getenv("DB_PASSWORD");   // <-- change this

    static {
        try { Class.forName("com.mysql.cj.jdbc.Driver"); }
        catch (ClassNotFoundException e) { throw new ExceptionInInitializerError(e); }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
