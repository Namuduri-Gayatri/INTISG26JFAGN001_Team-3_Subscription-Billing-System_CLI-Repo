package com.cognizant.dao;

import com.cognizant.exception.BillingException;
import com.cognizant.util.DBUtil;
import java.sql.*;
import java.util.*;

public class UserDAO {
    public void registerUser(String name, String email, String mobile, String password) throws BillingException {
        String sql = "INSERT INTO Users (name, email, mobile, password, created_at) VALUES (?, ?, ?, ?, NOW())";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, mobile);
            stmt.setString(4, password);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new BillingException("Error registering user: " + e.getMessage());
        }
    }

    public int authenticateUser(String mobile, String password) throws BillingException {
        String sql = "SELECT id FROM Users WHERE mobile=? AND password=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, mobile);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("id");
            else throw new BillingException("Invalid mobile or password!");
        } catch (SQLException e) {
            throw new BillingException("Error authenticating user: " + e.getMessage());
        }
    }


}
