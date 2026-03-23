package com.cognizant.dao;

import com.cognizant.exception.BillingException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class SubscriptionDAOTest {

    private SubscriptionDAO subscriptionDAO;

    @BeforeEach
    void setUp() {
        subscriptionDAO = new SubscriptionDAO();
    }

    @Test
    void testCreateSubscription() throws BillingException {
        int subscriptionId = subscriptionDAO.createSubscription(1, 1);
        assertTrue(subscriptionId > 0);
    }

    @Test
    void testViewSubscriptions() throws BillingException {
        assertDoesNotThrow(() -> subscriptionDAO.viewSubscriptions(1));
    }
}
