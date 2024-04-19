package com.example.creatingcontainer.Dto;

public class Product {

    public String productName;
    public String productVersion;
	public Product(String productName, String productVersion) {
		super();
		this.productName = productName;
		this.productVersion = productVersion;
	}
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductVersion() {
		return productVersion;
	}
	public void setProductVersion(String productVersion) {
		this.productVersion = productVersion;
	}
	
    
    
}
