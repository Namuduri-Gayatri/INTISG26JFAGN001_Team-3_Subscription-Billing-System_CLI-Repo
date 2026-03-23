package com.cognizant.dao;

import com.cognizant.exception.BillingException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class PlanDAOTest {

    private PlanDAO planDAO;

    @BeforeEach
    void setUp() {
        planDAO = new PlanDAO();
    }

    @Test
    void testAddPlan() throws BillingException {
        assertDoesNotThrow(() -> planDAO.addPlan("TestPlan", 199.0, 2, 1));
    }

    @Test
    void testGetAllPlans() throws BillingException {
        assertNotNull(planDAO.getAllPlans());
    }

    @Test
    void testUpdatePlan() throws BillingException {
        assertDoesNotThrow(() -> planDAO.updatePlan(1, "UpdatedPlan", 299.0, 3, 1));
    }
}
