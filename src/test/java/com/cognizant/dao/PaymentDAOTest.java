package com.cognizant.dao;

import com.cognizant.exception.BillingException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class PaymentDAOTest {

    private PaymentDAO paymentDAO;

    @BeforeEach
    void setUp() {
        paymentDAO = new PaymentDAO();
    }

    @Test
    void testMakePayment() throws BillingException {
        int paymentId = paymentDAO.makePayment(1);
        assertTrue(paymentId > 0);
    }

    @Test
    void testViewPayments() throws BillingException {
        assertDoesNotThrow(() -> paymentDAO.viewPayments(1));
    }
}
