// Author: Carolyn Rios
// Course: CS-320
// Instructor: Jeff Phillips

// Description: The "ContactServiceTest" class verifies that methods are working correctly.

package org.snhu.service.contact;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class ContactServiceTest {
	
	@BeforeEach
	void init() {
		ContactService.getInstance().contactDatabase.clear();
	}
	
	// Valid and Invalid Test Cases.
	
	@Test
	void testGetInstance() {
		assertTrue(ContactService.getInstance() != null);
	}
	
	@Test
	void testAdd() { // Method to test Add.
		Contact contact = new Contact("1", "First", "Last", "1234567788", "1234 Jupiter St");
		assertTrue(ContactService.getInstance().add(contact));
		assertTrue(ContactService.getInstance().contactDatabase.containsKey("1"));
	}
	
	@Test
	void testAddInvalid() { // Method to test for unhappy path Add.
		Contact contact = new Contact("1", "First", "Last", "1234567788", "1234 Jupiter St");
        ContactService.getInstance().add(contact);
        Contact duplicate = new Contact("1", "Other", "Name", "0000000000", "999 Other St");
        assertFalse(ContactService.getInstance().add(duplicate));
	}
	
	@Test
	void testDelete() { // Method to test Delete.
		Contact contact = new Contact("1", "First", "Last", "1234567788", "1234 Jupiter St");
		assertTrue(ContactService.getInstance().add(contact));
		assertTrue(ContactService.getInstance().delete("1"));
		assertFalse(ContactService.getInstance().contactDatabase.containsKey("1"));
	}
	
	@Test
    void testDeleteInvalid() { // Method to test for unhappy path Delete.
        assertFalse(ContactService.getInstance().delete("999"));
    }
	
	@Test
	void testUpdate() { // Method to test for Update.
		Contact contact = new Contact("1", "First", "Last", "1234567788", "1234 Jupiter St");
		assertTrue(ContactService.getInstance().add(contact));
		
		Contact updated = new Contact("1", "Mars", "Planet", "8765432211", "1234 Universe St");
		assertTrue(ContactService.getInstance().update("1", updated));
		
		Contact result = ContactService.getInstance().get("1");
		assertEquals("Mars", result.getFirstName());
        assertEquals("Planet", result.getLastName());
        assertEquals("8765432211", result.getPhone());
        assertEquals("1234 Universe St", result.getAddress());
	}
	
	@Test
    void testUpdateInvalid() { // Method to test for unhappy path Update.
        Contact updated = new Contact("0", "Rocky", "Eridian", "0000000000", "40 Eridani A");
        assertFalse(ContactService.getInstance().update("40", updated));
    }
	
	 @Test
	    void testGet() { // Method to test Get.
	        Contact contact = new Contact("1", "First", "Last", "1234567788", "1234 Jupiter St");
	        ContactService.getInstance().add(contact);
	        Contact result = ContactService.getInstance().get("1");
	        assertNotNull(result);
	        assertEquals("First", result.getFirstName());
	    }

	    @Test
	    void testGetNonExistent() { // Method to test for unhappy path Get.
	        assertNull(ContactService.getInstance().get("000"));
	    }

	    // Filtering Valid Test Cases.

	    @Test
	    void testFirstNameFilter() { // Filter by first name.
	        ContactService srv = ContactService.getInstance();
	        srv.add(new Contact("2", "Yáo", "Li-Jie", "2222222222", "2nd St"));
	        srv.add(new Contact("4", "Olesya", "Ilyukhina", "4444444444", "4th St"));
	        srv.add(new Contact("8", "Ryland", "Grace", "8888888888", "8th St"));

	        Contact filter = new Contact("D", "Eva", "Stratt", "1111111111", "1st St");
	        List<Contact> results = srv.queryContacts(filter, null, null, null);

	        assertEquals(2, results.size());
	        assertTrue(results.stream().allMatch(c -> c.getFirstName().equals("Ryland")));
	    }

	    @Test
	    void testFilterReturnsAll() { // Filter to return all contacts.
	        ContactService srv = ContactService.getInstance();
	        srv.add(new Contact("4", "Olesya", "Ilyukhina", "4444444444", "4th St"));
	        srv.add(new Contact("8", "Ryland", "Grace", "8888888888", "8th St"));

	        List<Contact> results = srv.queryContacts(null, null, null, null);
	        assertEquals(2, results.size());
	    }

	    // Partial Key Searching Test Cases.

	    @Test
	    void testPartialSearchFirstName() { // Partial Key matches first name.
	        ContactService srv = ContactService.getInstance();
	        srv.add(new Contact("4", "Olesya", "Ilyukhina", "4444444444", "4th St"));
	        srv.add(new Contact("8", "Ryland", "Grace", "8888888888", "8th St"));

	        List<Contact> results = srv.queryContacts(null, "ole", null, null);
	        assertEquals(1, results.size());
	        assertEquals("Olesya", results.get(0).getFirstName());
	    }

	    @Test
	    void testPartialSearchAddress() { // Partial Key matches address.
	        ContactService srv = ContactService.getInstance();
	        srv.add(new Contact("4", "Olesya", "Ilyukhina", "4444444444", "4th St"));
	        srv.add(new Contact("8", "Ryland", "Grace", "8888888888", "8th St"));

	        List<Contact> results = srv.queryContacts(null, "4th", null, null);
	        assertEquals(1, results.size());
	        assertEquals("Ryland", results.get(0).getFirstName());
	    }

	    @Test
	    void testPartialSearchMatch() { // Partial Key with no match.
	        ContactService srv = ContactService.getInstance();
	        srv.add(new Contact("4", "Olesya", "Ilyukhina", "4444444444", "4th St"));

	        List<Contact> results = srv.queryContacts(null, "ooo", null, null);
	        assertTrue(results.isEmpty());
	    }

	    // Sorting Algorithm Test Cases.

	    @Test
	    void testSortLastNameAscending() { // Ascending order.
	        ContactService srv = ContactService.getInstance();
	        srv.add(new Contact("2", "Yáo", "Li-Jie", "2222222222", "2nd St"));
	        srv.add(new Contact("4", "Olesya", "Ilyukhina", "4444444444", "4th St"));
	        srv.add(new Contact("8", "Ryland", "Grace", "8888888888", "8th St"));

	        List<Contact> results = srv.queryContacts(null, null, "lastName", "ASC");
	        assertEquals("Grace", results.get(0).getLastName());
	        assertEquals("Ilyukhina", results.get(1).getLastName());
	        assertEquals("Li-Jie", results.get(2).getLastName());
	    }

	    @Test
	    void testSortLastNameDescending() { // Descending order.
	        ContactService srv = ContactService.getInstance();
	        srv.add(new Contact("2", "Yáo", "Li-Jie", "2222222222", "2nd St"));
	        srv.add(new Contact("4", "Olesya", "Ilyukhina", "4444444444", "4th St"));
	        srv.add(new Contact("8", "Ryland", "Grace", "8888888888", "8th St"));

	        List<Contact> results = srv.queryContacts(null, null, "lastName", "DESC");
	        assertEquals("Li-Jie", results.get(0).getLastName());
	        assertEquals("Ilyukhina", results.get(1).getLastName());
	        assertEquals("Grace", results.get(2).getLastName());
	    }

	    @Test
	    void testSortByFirstNameAscending() { // A to Z order.
	        ContactService srv = ContactService.getInstance();
	        srv.add(new Contact("2", "Yáo", "Li-Jie", "2222222222", "2nd St"));
	        srv.add(new Contact("4", "Olesya", "Ilyukhina", "4444444444", "4th St"));
	        srv.add(new Contact("8", "Ryland", "Grace", "8888888888", "8th St"));

	        List<Contact> results = srv.queryContacts(null, null, "firstName", "ASC");
	        assertEquals("Ryland", results.get(0).getFirstName());
	        assertEquals("Olesya", results.get(1).getFirstName());
	        assertEquals("Yáo", results.get(2).getFirstName());
	    }

	    // All-In-One Tests.

	    @Test
	    void testFilterAndSort() { // Filter by first name, sort results by last name.
	        ContactService srv = ContactService.getInstance();
	        srv.add(new Contact("2", "Yáo", "Li-Jie", "2222222222", "2nd St"));
	        srv.add(new Contact("4", "Olesya", "Ilyukhina", "4444444444", "4th St"));
	        srv.add(new Contact("8", "Ryland", "Grace", "8888888888", "8th St"));

	        Contact filter = new Contact("D", "Eva", "Stratt", "1111111111", "1st St");
	        List<Contact> results = srv.queryContacts(filter, null, "lastName", "ASC");

	        assertEquals(2, results.size());
	        assertEquals("Grace", results.get(0).getLastName());
	        assertEquals("Ilyukhina", results.get(1).getLastName());
	    }

	    @Test
	    void testPartialSearchAndSort() { // Partial Key search and sort result by last name.
	        ContactService srv = ContactService.getInstance();
	        srv.add(new Contact("2", "Yáo", "Li-Jie", "2222222222", "2nd St"));
	        srv.add(new Contact("4", "Olesya", "Ilyukhina", "4444444444", "4th St"));
	        srv.add(new Contact("8", "Ryland", "Grace", "8888888888", "8th St"));

	        List<Contact> results = srv.queryContacts(null, "ole", "lastName", "DESC");

	        assertEquals(2, results.size());
	        assertEquals("Smith", results.get(0).getLastName());
	        assertEquals("Jones", results.get(1).getLastName());
	    }
	}
	
}