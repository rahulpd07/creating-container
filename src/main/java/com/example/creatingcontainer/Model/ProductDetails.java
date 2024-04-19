package com.example.creatingcontainer.Model;

public class ProductDetails {
 private String productName;
 private String productVersion;
public ProductDetails() {
	super();
	// TODO Auto-generated constructor stub
}
public ProductDetails(String productName, String productVersion) {
	super();
	this.productName = productName;
	this.productVersion = productVersion;
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
