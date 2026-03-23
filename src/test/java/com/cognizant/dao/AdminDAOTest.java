package com.cognizant.dao;

import com.cognizant.exception.BillingException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class AdminDAOTest {

    private AdminDAO adminDAO;

    @BeforeEach
    void setUp() {
        adminDAO = new AdminDAO();
    }

    @Test
    void testAuthenticateAdmin() throws BillingException {
        int adminId = adminDAO.authenticateAdmin("admin1", "admin1");
        assertEquals(1, adminId);
    }
}
