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
            throw new IllegalArgumentException("Invalid first name.");
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
    
    // Getters and setters.
    
    public String getContactID() {
        return contactID;
    }
    // Get first name.
    public String getFirstName() {
        return firstName;
    }
    // Set first name.
    public void setFirstName(String firstName) {
    	if (firstName == null || firstName.length() > 10) {
            throw new IllegalArgumentException("Invalid first name.");
        }
    	this.firstName = firstName;
    }
    // Get last name.
    public String getLastName() {
        return lastName;
    }
    // Set last name.
    public void setLastName(String lastName) {
    	if (lastName == null || lastName.length() > 10) {
            throw new IllegalArgumentException("Invalid last name.");
        }
    	this.lastName = lastName;
    }
    // Get phone.
    public String getPhone() {
        return phone;
    }
    // Set phone.
    public void setPhone(String phone) {
    	if (phone == null || phone.length() != 10 || !phone.matches("\\d{10}")) {
            throw new IllegalArgumentException("Invalid phone number.");
        }
    	this.phone = phone;
    }
    // Get address.
    public String getAddress() {
        return address;
    }
    // Set address.
    public void setAddress(String address) {
    	if (address == null || address.length() > 30) {
            throw new IllegalArgumentException("Invalid address.");
        }
    	this.address = address;
    }

}