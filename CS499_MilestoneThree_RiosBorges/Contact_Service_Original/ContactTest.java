// Author: Carolyn Rios
// Course: CS-320
// Instructor: Jeff Phillips

// Description: The "ContactTest" class verifies that methods are working correctly.

package org.snhu.service.contact;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ContactTest {
    
    @Test 
    void testContact() { // Test contact fields.
        Contact contact = new Contact("1", "First", "Last", "1234567788", "1234 Jupiter St");
        assertTrue(contact.getContactID() == "1");
        assertTrue(contact.getFirstName() == "First");
        assertTrue(contact.getLastName() == "Last");
        assertTrue(contact.getPhone() == "1234567788");
        assertTrue(contact.getAddress() == "1234 Jupiter St");
    }
    
    @Test
    void testSetter() { // Test setter fields.
    	Contact contact = new Contact("1", "First", "Last", "1234567788", "1234 Jupiter St");
    	contact.setFirstName("Jupiter");
    	contact.setLastName("Planet");
    	contact.setPhone("8765432211");
    	contact.setAddress("8765 Universe St");
    	
    	assertTrue(contact.getFirstName() == "Jupiter");
        assertTrue(contact.getLastName() == "Planet");
        assertTrue(contact.getPhone() == "8765432211");
        assertTrue(contact.getAddress() == "8765 Universe St");
    }
}