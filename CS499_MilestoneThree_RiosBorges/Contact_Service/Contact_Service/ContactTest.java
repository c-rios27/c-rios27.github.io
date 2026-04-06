// Author: Carolyn Rios
// Course: CS-320
// Instructor: Jeff Phillips

// Description: The "ContactTest" class verifies that methods are working correctly.

package org.snhu.service.contact;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ContactTest {
    
	// Valid Test Cases.
	
    @Test 
    void testContact() { // Test contact fields.
        Contact contact = new Contact("1", "First", "Last", "1234567788", "1234 Jupiter St");
        assertEquals("1", contact.getContactID());
        assertEquals("First", contact.getFirstName());
        assertEquals("Last", contact.getLastName());
        assertEquals("1234567788", contact.getPhone());
        assertEquals("1234 Jupiter St", contact.getAddress());
    }
    
    @Test
    void testSetter() { // Test setter fields.
    	Contact contact = new Contact("1", "First", "Last", "1234567788", "1234 Jupiter St");
    	contact.setFirstName("Jupiter");
    	contact.setLastName("Planet");
    	contact.setPhone("8765432211");
    	contact.setAddress("8765 Universe St");
    	
    	assertEquals("Jupiter", contact.getFirstName());
        assertEquals("Planet", contact.getLastName());
        assertEquals("8765432211", contact.getPhone());
        assertEquals("8765 Universe St", contact.getAddress());
    }
    
    // Invalid Test Cases. Throw Exceptions.
    @Test
    void testNullContactID() { // Contact ID cannot be null.
        assertThrows(IllegalArgumentException.class, () ->
            new Contact(null, "First", "Last", "1234567788", "1234 Jupiter St"));
    }

    @Test
    void testContactIDLength() { // Contact ID cannot be longer than 10 characters.
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("12345678901", "First", "Last", "1234567788", "1234 Jupiter St"));
    }

    @Test
    void testNullFirstName() { // First Name cannot be null.
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("1", null, "Last", "1234567788", "1234 Jupiter St"));
    }

    @Test
    void testFirstNameTooLength() { // First Name cannot be longer than 10 characters.
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("1", "FirstNameTooLong", "Last", "1234567788", "1234 Jupiter St"));
    }

    @Test
    void testNullLastName() { // Last Name cannot be null.
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("1", "First", null, "1234567788", "1234 Jupiter St"));
    }

    @Test
    void testLastNameLength() { // Last Name cannot be longer than 10 characters.
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("1", "First", "LastNameTooLong", "1234567788", "1234 Jupiter St"));
    }

    @Test
    void testNullPhone() { // Phone cannot be null.
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("1", "First", "Last", null, "1234 Jupiter St"));
    }

    @Test
    void testPhoneLength() { // Phone must be exactly 10 digits.
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("1", "First", "Last", "12345678901", "1234 Jupiter St"));
    }

    @Test
    void testNullAddress() { // Address cannot be null.
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("1", "First", "Last", "1234567788", null));
    }

    @Test
    void testAddressLength() { // Address cannot be longer than 30 characters.
        assertThrows(IllegalArgumentException.class, () ->
            new Contact("1", "First", "Last", "1234567788", "This address is longer than 30 characters and must not be here"));
    }
}