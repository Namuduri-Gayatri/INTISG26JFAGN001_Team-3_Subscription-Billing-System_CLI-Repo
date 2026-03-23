package com.cognizant.dao;

import com.cognizant.exception.BillingException;
import com.cognizant.util.DBUtil;
import java.sql.*;

public class SubscriptionDAO {
    public int createSubscription(int userId, int planId) throws BillingException {
        String sql = "INSERT INTO Subscriptions (user_id, plan_id, start_date, end_date, status) " +
                     "VALUES (?, ?, NOW(), DATE_ADD(NOW(), INTERVAL (SELECT duration_months FROM Plans WHERE id=?) MONTH), 'ACTIVE')";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, planId);
            stmt.setInt(3, planId);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
            else throw new BillingException("Failed to create subscription");
        } catch (SQLException e) {
            throw new BillingException("Error creating subscription: " + e.getMessage());
        }
    }

    public void viewSubscriptions(int userId) throws BillingException {
        String sql = "SELECT s.id, p.name, s.start_date,p.duration_months, s.end_date, s.status " +
                     "FROM Subscriptions s JOIN Plans p ON s.plan_id=p.id WHERE s.user_id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Subscription ID: " + rs.getInt("id") +
                                   ", Plan: " + rs.getString("name") +
                                   ", Start: " + rs.getTimestamp("start_date") +
                                   ", End: " + rs.getTimestamp("end_date") +
                                   ", Duration: " + rs.getInt("duration_months") + "months"+
                                   ", Status: " + rs.getString("status"));
            }
        } catch (SQLException e) {
            throw new BillingException("Error viewing subscriptions: " + e.getMessage());
        }
    }
}
