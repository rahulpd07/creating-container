package com.example.creatingcontainer.Dto.GlobalController;

import java.util.List;

import com.example.creatingcontainer.Model.ProductDetails;

public class ReturnTenantData {
  private String deploymentId;
  private String tenantId;
  List<ProductDetails>productDetails;
public ReturnTenantData() {
	super();
	// TODO Auto-generated constructor stub
}
public ReturnTenantData(String deploymentId, String tenantId, List<ProductDetails> productDetails) {
	super();
	this.deploymentId = deploymentId;
	this.tenantId = tenantId;
	this.productDetails = productDetails;
}
public String getDeploymentId() {
	return deploymentId;
}
public void setDeploymentId(String deploymentId) {
	this.deploymentId = deploymentId;
}
public String getTenantId() {
	return tenantId;
}
public void setTenantId(String tenantId) {
	this.tenantId = tenantId;
}
public List<ProductDetails> getProductDetails() {
	return productDetails;
}
public void setProductDetails(List<ProductDetails> productDetails) {
	this.productDetails = productDetails;
}
 

 
	 
}
