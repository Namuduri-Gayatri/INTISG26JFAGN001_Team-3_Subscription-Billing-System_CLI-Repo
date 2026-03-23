package com.cognizant.dao;

import com.cognizant.exception.BillingException;
import com.cognizant.util.DBUtil;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class PlanDAO {
    public void addPlan(String name, double price, int durationMonths, int adminId) throws BillingException {
        String sql = "INSERT INTO Plans (name, price, duration_months, created_at) VALUES (?, ?, ?, NOW())";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setInt(3, durationMonths);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new BillingException("Error adding plan: " + e.getMessage());
        }
    }

    public void updatePlan(int planId, String name, double price, int durationMonths, int adminId) throws BillingException {
        String sql = "UPDATE Plans SET name=?, price=?, duration_months=?, updated_at=NOW() WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setInt(3, durationMonths);
            stmt.setInt(4, planId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new BillingException("Error updating plan: " + e.getMessage());
        }
    }

    public List<String> getAllPlans() throws BillingException {
        List<String> plans = new ArrayList<>();
        String sql = "SELECT * FROM Plans";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                plans.add(rs.getInt("id") + ". " + rs.getString("name") + " - ₹" + rs.getDouble("price") + " - " + rs.getInt("duration_months") + "months");
            }
        } catch (SQLException e) {
            throw new BillingException("Error fetching plans: " + e.getMessage());
        }
        return plans;
    }

    public void showPlanDetails(int planId) throws BillingException {
        String sql = "SELECT name, price, duration_months FROM Plans WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, planId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String planName = rs.getString("name");
                double price = rs.getDouble("price");
                int duration = rs.getInt("duration_months");

                LocalDate paymentDate = LocalDate.now();
                LocalDate validTill = paymentDate.plusMonths(duration);

                System.out.println("Subscription successful for the plan "
                        + planName + " (" + price + ").");
                System.out.println("Payment date - " + paymentDate
                        + ", duration - " + duration + " months, valid till - " + validTill + ".");
                System.out.println("Invoice generation successful.");
            } else {
                throw new BillingException("Plan not found");
            }
        } catch (SQLException e) {
            throw new BillingException("Error fetching plan details: " + e.getMessage());
        }
    }

}
