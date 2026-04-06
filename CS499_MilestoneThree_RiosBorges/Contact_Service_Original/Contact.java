// Author: Carolyn Rios
// Course: CS-320
// Instructor: Jeff Phillips

// Description: The "Contact" class creates and stores the information of contacts.

package org.snhu.service.contact;

public class Contact {
    private String contactID;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;

    public Contact(String contactID, String firstName, String lastName, String phone, String address) {
    	// Required contact ID. Cannot be longer than 10 characters, cannot be null, cannot be updated. 
        if(contactID == null || contactID.length()>10) {
            throw new IllegalArgumentException("Invalid contact ID.");
        }
        // Required first name. Cannot be longer than 10 characters, cannot be null.
        if(firstName == null || firstName.length()>10) {
            throw new IllegalArgumentException("Invalid Invalid first name.");
        }
        // Required last name. Cannot be longer than 10 character, cannot be null.
        if(lastName == null || lastName.length()>10) {
            throw new IllegalArgumentException("Invalid last name.");
        }
        // Required phone number. Must be exactly 10 digits, cannot be null.
        if(phone == null || phone.length()>10) {
            throw new IllegalArgumentException("Invalid phone number.");
        }
        // Required address. Cannot be longer than 30 characters, cannot be null.
        if(address == null || address.length()>30) {
            throw new IllegalArgumentException("Invalid address.");
        }

        this.contactID = contactID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
    }
    
    // Get contact ID, first name, last name, phone number, and address.
    // Set contact ID, first name, last name, phone number, and address.
    public String getContactID() {
        return contactID;
    }

    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
    	this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
    	this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
    	this.phone = phone;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
    	this.address = address;
    }

}