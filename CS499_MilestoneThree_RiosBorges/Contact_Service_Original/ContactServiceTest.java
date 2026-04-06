// Author: Carolyn Rios
// Course: CS-320
// Instructor: Jeff Phillips

// Description: The "ContactServiceTest" class verifies that methods are working correctly.

package org.snhu.service.contact;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ContactServiceTest {
	
	@BeforeEach
	void init() {
		ContactService.getInstance().contactDatabase.clear();
	}
	
	@Test
	void testGetInstance() {
		assertTrue(ContactService.getInstance() != null);
	}
	
	@Test
	void testAdd() {
		Contact contact = new Contact("1", "First", "Last", "1234567788", "1234 Jupiter St");
		assertTrue(ContactService.getInstance().add(contact));
		assertTrue(ContactService.getInstance().contactDatabase.containsKey("1"));
	}
	
	@Test
	void testDelete() {
		Contact contact = new Contact("1", "First", "Last", "1234567788", "1234 Jupiter St");
		assertTrue(ContactService.getInstance().add(contact));
		assertTrue(ContactService.getInstance().delete("1"));
		ContactService srv = ContactService.getInstance();
		assertTrue(srv.contactDatabase.containsKey("1") == false);
	}
	
	@Test
	void testUpdate() {
		Contact contact = new Contact("1", "First", "Last", "1234567788", "1234 Jupiter St");
		assertTrue(ContactService.getInstance().add(contact));
		
		Contact updated = new Contact("1", "Mars", "Planet", "8765432211", "1234 Universe St");
		assertTrue(ContactService.getInstance().update("1", updated));
		
		ContactService srv = ContactService.getInstance();
		Contact tst = srv.contactDatabase.get("1");
		
		assertTrue(tst.getFirstName() == "Mars");
		assertTrue(tst.getLastName() == "Planet");
		assertTrue(tst.getPhone() == "8765432211");
		assertTrue(tst.getAddress() == "1234 Universe St");

	}
}