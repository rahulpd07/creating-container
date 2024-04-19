package com.example.creatingcontainer.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "internal_data")
public class InternalDataModel {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	long id;
	@Column(name = "deployment_Id")
	private String deploymentId;
	@Column(name = "sdn_Ip")
	private String  sdnIp;
	@Column(name = "sdn_Port")
	private String sdnPort;
	@Column(name = "five_G_core_Ip")
	private String  five5GcoreIp;
	@Column(name = "five_G_Core_Port")
	private String fiveGCorePort;
	@Column(name = "docker_Ip")
	private String dockerIp;
	@Column(name = "docker_Port")
	private String dockerPort;
	@Column(name = "tenant_Id")
	private String tenantId;
	@Column(name ="global_Controller_Ip")
	private String globalControllerIp;
	@Column(name = "global_Controller_Port")
	private String globalControllerPort;
	
public InternalDataModel(String deploymentId) {
		 
		this.deploymentId = deploymentId;
	}

public InternalDataModel() {
	super();
	// TODO Auto-generated constructor stub
}

public InternalDataModel(long id, String deploymentId, String sdnIp, String sdnPort, String five5GcoreIp,
		String fiveGCorePort, String dockerIp, String dockerPort, String tenantId, String globalControllerIp,
		String globalControllerPort) {
	super();
	this.id = id;
	this.deploymentId = deploymentId;
	this.sdnIp = sdnIp;
	this.sdnPort = sdnPort;
	this.five5GcoreIp = five5GcoreIp;
	this.fiveGCorePort = fiveGCorePort;
	this.dockerIp = dockerIp;
	this.dockerPort = dockerPort;
	this.tenantId = tenantId;
	this.globalControllerIp = globalControllerIp;
	this.globalControllerPort = globalControllerPort;
}

public long getId() {
	return id;
}

public void setId(long id) {
	this.id = id;
}

public String getDeploymentId() {
	return deploymentId;
}

public void setDeploymentId(String deploymentId) {
	this.deploymentId = deploymentId;
}

public String getSdnIp() {
	return sdnIp;
}

public void setSdnIp(String sdnIp) {
	this.sdnIp = sdnIp;
}

public String getSdnPort() {
	return sdnPort;
}

public void setSdnPort(String sdnPort) {
	this.sdnPort = sdnPort;
}

public String getFive5GcoreIp() {
	return five5GcoreIp;
}

public void setFive5GcoreIp(String five5GcoreIp) {
	this.five5GcoreIp = five5GcoreIp;
}

public String getFiveGCorePort() {
	return fiveGCorePort;
}

public void setFiveGCorePort(String fiveGCorePort) {
	this.fiveGCorePort = fiveGCorePort;
}

public String getDockerIp() {
	return dockerIp;
}

public void setDockerIp(String dockerIp) {
	this.dockerIp = dockerIp;
}

public String getDockerPort() {
	return dockerPort;
}

public void setDockerPort(String dockerPort) {
	this.dockerPort = dockerPort;
}

public String getTenantId() {
	return tenantId;
}

public void setTenantId(String tenantId) {
	this.tenantId = tenantId;
}

public String getGlobalControllerIp() {
	return globalControllerIp;
}

public void setGlobalControllerIp(String globalControllerIp) {
	this.globalControllerIp = globalControllerIp;
}

public String getGlobalControllerPort() {
	return globalControllerPort;
}

public void setGlobalControllerPort(String globalControllerPort) {
	this.globalControllerPort = globalControllerPort;
}
 

 
	 

 
}
