# Subscription Billing System (CLI)

A command-line based subscription billing system built with **Java** and **Maven**.  
This project demonstrates how to manage users, plans, subscriptions, payments, and invoices using JDBC with a MySQL database.

---

## Project Structure

src/
├── main/java/com/cognizant/
│    ├── SubscriptionBillingSystem/App.java      # CLI entry point
│    ├── dao/                                   # Data Access Objects
│    │    ├── AdminDAO.java
│    │    ├── PlanDAO.java
│    │    ├── UserDAO.java
│    │    ├── SubscriptionDAO.java
│    │    ├── PaymentDAO.java
│    │    └── InvoiceDAO.java
│    ├── exception/BillingException.java        # Custom exception
│    └── util/DBUtil.java                       # Database connection utility
│
└── test/java/com/cognizant/dao/                # JUnit test classes

## Features

1. User Management: Register and authenticate users.

2. Admin Management: Authenticate admins and manage plans.

3. Plan Management: Add, update, and list subscription plans.

4. Subscription Management: Create subscriptions, view active ones.

5. Payments: Process payments linked to subscriptions.

6. Invoices: Generate and view invoices with plan details.

## Running Tests

JUnit 5 is used for unit testing. 
Test classes include:

1. PlanDAOTest

2. UserDAOTest

3. AdminDAOTest

4. SubscriptionDAOTest

5. PaymentDAOTest

6. InvoiceDAOTest

7. AppTest
