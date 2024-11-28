package com.yrgo.integrationtests;

import com.yrgo.data.RecordNotFoundException;
import com.yrgo.domain.Customer;
import com.yrgo.services.customers.CustomerManagementService;
import com.yrgo.services.customers.CustomerNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(SpringExtension.class)
@ContextConfiguration({"/other-tiers.xml", "/datasource-test.xml"})
@Transactional
public class CustomerManagementIntegrationTests {

    @Autowired
    private CustomerManagementService service;

    @Test
    public void testFindExistingCustomer() {
        // Arrange
        String customerId = "1";
        String companyName = "ACME";
        String notes = "Great customer";
        Customer testCustomer = new Customer(customerId, companyName, notes);
        service.newCustomer(testCustomer);

        // Act
        Customer existingCustomer = null;
        try {
            existingCustomer = service.findCustomerById("1");
            assertEquals(testCustomer, existingCustomer);
        } catch (CustomerNotFoundException | RecordNotFoundException e) {
            fail("No customer was found when one should have been!");
        }
    }

    @Test
    public void testAddNewCustomer() {
        service.newCustomer(new Customer("1", "ACME", "Great customer"));

        int customersInDb = service.getAllCustomers().size();
        assertEquals(1, customersInDb);
    }


}
