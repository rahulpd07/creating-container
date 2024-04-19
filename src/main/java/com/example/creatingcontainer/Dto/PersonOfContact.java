package com.example.creatingcontainer.Dto;

public class PersonOfContact {

	
    public String fullName;
    public String contact;
    public String email;
	public PersonOfContact(String fullName, String contact, String email) {
		super();
		this.fullName = fullName;
		this.contact = contact;
		this.email = email;
	}
	public PersonOfContact() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
    
    
}
