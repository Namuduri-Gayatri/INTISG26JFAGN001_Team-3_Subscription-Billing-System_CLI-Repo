package com.cognizant.SubscriptionBillingSystem;

/**
 * Hello world!
 */
import com.cognizant.dao.*;

import com.cognizant.exception.BillingException;
import java.util.*;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PlanDAO planDAO = new PlanDAO();
        AdminDAO adminDAO = new AdminDAO();
        UserDAO userDAO = new UserDAO();
        SubscriptionDAO subscriptionDAO = new SubscriptionDAO();
        PaymentDAO paymentDAO = new PaymentDAO();
        InvoiceDAO invoiceDAO = new InvoiceDAO();

        while (true) {
            System.out.println("\n--- Subscription Billing CLI ---");
            System.out.println("1. Add Plan (Admin)");
            System.out.println("2. Update Plan (Admin)");
            System.out.println("3. View Plans");
            System.out.println("4. User Registration");
            System.out.println("5. Subscribe to Plan & Make Payment");
            System.out.println("6. View Payments");
            System.out.println("7. View Invoices");
            System.out.println("8. View Subscriptions");
            System.out.println("9. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();

            try {
                switch (choice) {
                    case 1:
                    	int adminId = authenticateAdmin(sc, adminDAO);
                    	if(adminId==1) {
	                        System.out.print("Enter Plan Name: ");
	                        String name = sc.next();
	                        System.out.print("Enter Price: ");
	                        double price = sc.nextDouble();
	                        System.out.print("Enter Duration (months): ");
	                        int duration = sc.nextInt();
	                        planDAO.addPlan(name, price, duration, adminId);
	                        System.out.println("Plan added successfully!");
                    	}
                    	else {
                    		System.out.println("Accessible to admins only!");
                    	}
                        break;
                    case 2:
                    	adminId = authenticateAdmin(sc, adminDAO);
                    	if(adminId==1) {
	                        System.out.print("Enter Plan ID: ");
	                        int planId = sc.nextInt();
	                        System.out.print("Enter New Name: ");
	                        String name = sc.next();
	                        System.out.print("Enter New Price: ");
	                        double price = sc.nextDouble();
	                        System.out.print("Enter New Duration: ");
	                        int duration = sc.nextInt();
	                        planDAO.updatePlan(planId, name, price, duration, adminId);
	                        System.out.println("Plan updated successfully!");
                    	}
                    	else {
                    		System.out.println("Accessible to admins only!");
                    	}
                        break;
                    case 3:
                        System.out.println("Available Plans:");
                        for (String plan : planDAO.getAllPlans()) System.out.println(plan);
                        break;
                    case 4:
                        System.out.print("Enter Name: ");
                        String uname = sc.next();
                        System.out.print("Enter Email: ");
                        String email = sc.next();
                        System.out.print("Enter Mobile: ");
                        String mobile = sc.next();
                        System.out.print("Enter Password: ");
                        String pwd = sc.next();
                        userDAO.registerUser(uname, email, mobile, pwd);
                        System.out.println("User registered successfully!");
                        break;
                    case 5:
                        int userId = authenticate(sc, userDAO);
                        System.out.println("Available Plans:");
                        for (String plan : planDAO.getAllPlans()) System.out.println(plan);
                        System.out.print("Select Plan ID: ");
                        int selectedPlanId = sc.nextInt();
                        int subscriptionId = subscriptionDAO.createSubscription(userId, selectedPlanId);
                        int paymentId = paymentDAO.makePayment(subscriptionId);
                        invoiceDAO.generateInvoice(paymentId);
                        planDAO.showPlanDetails(selectedPlanId);
                        break;
                    case 6:
                        userId = authenticate(sc, userDAO);
                        paymentDAO.viewPayments(userId);
                        break;
                    case 7:
                        userId = authenticate(sc, userDAO);
                        invoiceDAO.viewInvoices(userId);
                        break;
                    case 8:
                        userId = authenticate(sc, userDAO);
                        subscriptionDAO.viewSubscriptions(userId);
                        break;
                    case 9:
                        System.exit(0);
                }
            } catch (BillingException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static int authenticateAdmin(Scanner sc, AdminDAO adminDAO) throws BillingException {
        System.out.print("Enter Admin Username: ");
        String username = sc.next();
        System.out.print("Enter Admin Password: ");
        String password = sc.next();
        return adminDAO.authenticateAdmin(username, password);
    }
    
    private static int authenticate(Scanner sc, UserDAO userDAO) throws BillingException {
        System.out.print("Enter Mobile: ");
        String mobile = sc.next();
        System.out.print("Enter Password: ");
        String password = sc.next();
        return userDAO.authenticateUser(mobile, password);
    }
}
