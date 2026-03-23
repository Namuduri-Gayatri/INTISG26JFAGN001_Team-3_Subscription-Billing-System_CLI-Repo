package com.cognizant.dao;

import com.cognizant.exception.BillingException;
import com.cognizant.util.DBUtil;
import java.sql.*;

public class InvoiceDAO {
    public void generateInvoice(int paymentId) throws BillingException {
        String details = "Invoice generation SUCCESS for Payment ID " + paymentId;
        String sql = "INSERT INTO Invoices (payment_id, invoice_date, details) VALUES (?, NOW(), ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, paymentId);
            stmt.setString(2, details);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new BillingException("Error generating invoice: " + e.getMessage());
        }
    }

    public void viewInvoices(int userId) throws BillingException {
        String sql = "SELECT i.id, p.id AS payment_id, i.invoice_date,p.amount,pl.name,pl.duration_months, i.details " +
                     "FROM Invoices i JOIN Payments p ON i.payment_id=p.id " +
                     "JOIN Subscriptions s ON p.subscription_id=s.id JOIN Plans pl ON s.plan_id = pl.id WHERE s.user_id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Invoice ID: " + rs.getInt("id") +
                                   ", Payment ID: " + rs.getInt("payment_id") +
                                   ", Plan Name: " + rs.getString("name") +
                                   ", Amount: " + rs.getDouble("amount") +
                                   ", Duration: " + rs.getInt("duration_months") +"months"+
                                   ", Date: " + rs.getTimestamp("invoice_date") +
                                   ", Details: " + rs.getString("details"));
            }
        } catch (SQLException e) {
            throw new BillingException("Error viewing invoices: " + e.getMessage());
        }
    }
}
