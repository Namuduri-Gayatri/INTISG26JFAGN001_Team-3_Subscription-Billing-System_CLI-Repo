package com.cognizant.dao;

import com.cognizant.exception.BillingException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    private UserDAO userDAO;

    @BeforeEach
    void setUp() {
        userDAO = new UserDAO();
    }

    @Test
    void testRegisterUser() throws BillingException {
        assertDoesNotThrow(() -> userDAO.registerUser("TestUser", "test@example.com", "9999999999", "password"));
    }

    @Test
    void testAuthenticateUser() throws BillingException {
        int userId = userDAO.authenticateUser("9999999999", "password");
        assertTrue(userId > 0);
    }
}
