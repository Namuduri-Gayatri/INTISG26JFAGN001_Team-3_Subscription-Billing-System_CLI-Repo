package com.cognizant.dao;

import com.cognizant.exception.BillingException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceDAOTest {

    private InvoiceDAO invoiceDAO;

    @BeforeEach
    void setUp() {
        invoiceDAO = new InvoiceDAO();
    }

    @Test
    void testGenerateInvoice() throws BillingException {
        assertDoesNotThrow(() -> invoiceDAO.generateInvoice(1));
    }

    @Test
    void testViewInvoices() throws BillingException {
        assertDoesNotThrow(() -> invoiceDAO.viewInvoices(1));
    }
}
