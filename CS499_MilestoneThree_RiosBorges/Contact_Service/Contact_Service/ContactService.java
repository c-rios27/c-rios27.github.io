// Author: Carolyn Rios
// Course: CS-320
// Instructor: Jeff Phillips

// Description: The "ContactService" class stores the information of contacts.
// It can also add, delete, and update contacts.

package org.snhu.service.contact;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

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
		Contact existing = contactDatabase.get(contactID);
		
		if (existing == null) return false;
		
		existing.setFirstName(updated.getFirstName());
		existing.setLastName(updated.getLastName());
		existing.setPhone(updated.getPhone());
		existing.setAddress(updated.getAddress());
		
		return true;
	}
	
	public Contact get(String contactID) { // Obtain contact by its ID.
		return contactDatabase.get(ContactID);
	}
	
	public List<Contact> queryContacts(Contact filter, String keyword, String sortField, String sortOrder) {
		List<Contact> results = new ArrayList<>(contactDatabase.values());
		
		// Filtering.
		if (filter != null) {
			results = filterContacts(results, filter);
		}
		
		// Partial Key Searching.
		if (keyword != null && !keyword.isEmpty() ) {
			List<Contact> keywordMatches = partialSearch(results, keyword);
			results.retainAll(keywordMatches);
		}
		
		// Sorting Algorithm.
		if (sortingField != null && !sortField.isEmpty()) {
			results = sortContacts(results, sortField, sortOrder);
		}
		
		return results;
	}
	
	// Returns contacts where every non-null filter field matches exactly.
    private List<Contact> filterContacts(List<Contact> contacts, Contact filter) { // Filtered.
        List<Contact> filtered = new ArrayList<>();

        for (Contact c : contacts) {
            boolean matches = true;

            if (filter.getFirstName() != null && !filter.getFirstName().equals(c.getFirstName())) matches = false;
            if (filter.getLastName()  != null && !filter.getLastName().equals(c.getLastName()))   matches = false;
            if (filter.getPhone()     != null && !filter.getPhone().equals(c.getPhone()))         matches = false;
            if (filter.getAddress()   != null && !filter.getAddress().equals(c.getAddress()))     matches = false;

            if (matches) filtered.add(c);
        }

        return filtered;
    }

    private List<Contact> partialSearch(List<Contact> contacts, String keyword) { // Partial Key Searching.
        String lower = keyword.toLowerCase();
        List<Contact> matches = new ArrayList<>();

        for (Contact c : contacts) {
            if (c.getFirstName().toLowerCase().contains(lower) ||
                c.getLastName().toLowerCase().contains(lower)  ||
                c.getPhone().toLowerCase().contains(lower)     ||
                c.getAddress().toLowerCase().contains(lower)) {
                matches.add(c);
            }
        }

        return matches;
    }

    private List<Contact> sortContacts(List<Contact> contacts, String sortField, String sortOrder) { // Sorting algorithm.
        Comparator<Contact> comparator;

        switch (sortField.toLowerCase()) {
            case "firstname": comparator = Comparator.comparing(Contact::getFirstName); break;
            case "lastname":  comparator = Comparator.comparing(Contact::getLastName);  break;
            case "phone":     comparator = Comparator.comparing(Contact::getPhone);     break;
            case "address":   comparator = Comparator.comparing(Contact::getAddress);   break;
            default: return contacts; // Unknown field — return unsorted.
        }

        if ("DESC".equalsIgnoreCase(sortOrder)) {
            comparator = comparator.reversed();
        }

        return contacts.stream().sorted(comparator).collect(Collectors.toList());
    }
}