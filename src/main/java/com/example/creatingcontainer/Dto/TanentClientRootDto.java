package com.example.creatingcontainer.Dto;

import java.util.ArrayList;

import com.example.creatingcontainer.Model.AllProductDetails;

public class TanentClientRootDto {

	public String deploymentId;
	
	public String tenantId;
	
	public ArrayList<AllProductDetails> productDetails;

	public TanentClientRootDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TanentClientRootDto(String deploymentId, String tenantId, ArrayList<AllProductDetails> productDetails) {
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

	public ArrayList<AllProductDetails> getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(ArrayList<AllProductDetails> productDetails) {
		this.productDetails = productDetails;
	}
	 
	
}
