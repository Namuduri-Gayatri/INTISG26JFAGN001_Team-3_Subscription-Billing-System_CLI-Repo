package com.cognizant.dao;

import com.cognizant.exception.BillingException;
import com.cognizant.util.DBUtil;
import java.sql.*;

public class AdminDAO {
    public int authenticateAdmin(String username, String password) throws BillingException {
        String sql = "SELECT id FROM Admins WHERE username=? AND password=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id"); // return adminId
            } else {
                throw new BillingException("Invalid admin credentials!");
            }
        } catch (SQLException e) {
            throw new BillingException("Error authenticating admin: " + e.getMessage());
        }
    }
}
