package com.example.creatingcontainer.Dto;

public class Address {

	    public String streetName;
	    public String city;
	    public String pinCode;
	    public String state;
	    public String country;
		public String getStreetName() {
			return streetName;
		}
		public void setStreetName(String streetName) {
			this.streetName = streetName;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getPinCode() {
			return pinCode;
		}
		public void setPinCode(String pinCode) {
			this.pinCode = pinCode;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public Address() {
			super();
			// TODO Auto-generated constructor stub
		}
		public Address(String streetName, String city, String pinCode, String state, String country) {
			super();
			this.streetName = streetName;
			this.city = city;
			this.pinCode = pinCode;
			this.state = state;
			this.country = country;
		}
	    
	    
	
}
