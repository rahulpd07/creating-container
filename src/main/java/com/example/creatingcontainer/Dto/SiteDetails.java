package com.example.creatingcontainer.Dto;

public class SiteDetails {

	
	    public String siteName;
	    public Address address;
	    public PersonOfContact personOfContact;
		public SiteDetails(String siteName, Address address, PersonOfContact personOfContact) {
			super();
			this.siteName = siteName;
			this.address = address;
			this.personOfContact = personOfContact;
		}
		public SiteDetails() {
			super();
			// TODO Auto-generated constructor stub
		}
		public String getSiteName() {
			return siteName;
		}
		public void setSiteName(String siteName) {
			this.siteName = siteName;
		}
		public Address getAddress() {
			return address;
		}
		public void setAddress(Address address) {
			this.address = address;
		}
		public PersonOfContact getPersonOfContact() {
			return personOfContact;
		}
		public void setPersonOfContact(PersonOfContact personOfContact) {
			this.personOfContact = personOfContact;
		}
	    
	    
}
