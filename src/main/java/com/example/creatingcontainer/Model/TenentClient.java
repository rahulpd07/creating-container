package com.example.creatingcontainer.Model;


public class TenentClient {

	private String deploymentId;
	private String tenantId;
	public TenentClient() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TenentClient(String deploymentId, String tenantId) {
		super();
		this.deploymentId = deploymentId;
		this.tenantId = tenantId;
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
    
	
}
