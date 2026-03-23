package com.cognizant.dao;

import com.cognizant.exception.BillingException;
import com.cognizant.util.DBUtil;
import java.sql.*;

public class PaymentDAO {
    public int makePayment(int subscriptionId) throws BillingException {
        // Fetch plan price
        String fetchSql = "SELECT p.price FROM Subscriptions s JOIN Plans p ON s.plan_id=p.id WHERE s.id=?";
        double amount;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(fetchSql)) {
            stmt.setInt(1, subscriptionId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                amount = rs.getDouble("price");
            } else {
                throw new BillingException("Subscription not found!");
            }
        } catch (SQLException e) {
            throw new BillingException("Error fetching subscription price: " + e.getMessage());
        }

        // Insert payment record
        String sql = "INSERT INTO Payments (subscription_id, amount, payment_date, status) VALUES (?, ?, NOW(), 'SUCCESS')";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, subscriptionId);
            stmt.setDouble(2, amount);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
            else throw new BillingException("Failed to record payment");
        } catch (SQLException e) {
            throw new BillingException("Error making payment: " + e.getMessage());
        }
    }

    public void viewPayments(int userId) throws BillingException {
        String sql = "SELECT p.id, s.id AS subscription_id, p.amount,pl.name, p.payment_date, p.status " +
                     "FROM Payments p JOIN Subscriptions s ON p.subscription_id=s.id JOIN Plans pl ON s.plan_id = pl.id WHERE s.user_id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Payment ID: " + rs.getInt("id") +
                                   ", Plan: " + rs.getString("name") +
                                   ", Amount: ₹" + rs.getDouble("amount") +
                                   ", Date: " + rs.getTimestamp("payment_date") +
                                   ", Status: " + rs.getString("status"));
            }
        } catch (SQLException e) {
            throw new BillingException("Error viewing payments: " + e.getMessage());
        }
    }
}
