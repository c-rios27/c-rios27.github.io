// Author: Carolyn Rios
// Course: CS-320
// Instructor: Jeff Phillips

// Description: The "ContactService" class stores the information of contacts.
// It can also add, delete, and update contacts.

package org.snhu.service.contact;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ContactService {
	private static ContactService INSTANCE;
	
	Map<String, Contact> contactDatabase = new ConcurrentHashMap<>();
	
	private ContactService() {
		
	}
	
	public static synchronized ContactService getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ContactService();
		}
		return INSTANCE;
	}
	
	public boolean add(Contact contact) { // Add contact.
		return contactDatabase.putIfAbsent(contact.getContactID(), contact) == null;
	}
	
	public boolean delete(String contactID) { // Delete contact.
		return contactDatabase.remove(contactID) != null;
	}
	
	public boolean update(String contactID, Contact updated) { // Update contact.
		// throw new UnsupportedOperationException();
		Contact existing = contactDatabase.get(contactID);
		
		if (existing == null) return false;
		
		existing.setFirstName(updated.getFirstName());
		existing.setLastName(updated.getLastName());
		existing.setPhone(updated.getPhone());
		existing.setAddress(updated.getAddress());
		
		return true;
	}
}