package com.example.creatingcontainer.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name="networkconfiguration")
public class NetworkConfiguration {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	Long Id;
	@Column(name="n2ip")
	private String n2Ip;
	@Column(name="n3ip")
	private String n3Ip;
	@Column(name="gatewayip")
	private String GatewayIp;
	@Column(name="docker_fivegcore_version")
	private String dockerfivegcoreversion;
	@Column(name="internet_networking")
	private String internetNetworking;
	@Column(name = "local_controller_client_id")
	String localControllerClientId;
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getN2Ip() {
		return n2Ip;
	}
	public void setN2Ip(String n2Ip) {
		this.n2Ip = n2Ip;
	}
	public String getN3Ip() {
		return n3Ip;
	}
	public void setN3Ip(String n3Ip) {
		this.n3Ip = n3Ip;
	}
	public String getGatewayIp() {
		return GatewayIp;
	}
	public void setGatewayIp(String gatewayIp) {
		GatewayIp = gatewayIp;
	}
	public String getDockerfivegcoreversion() {
		return dockerfivegcoreversion;
	}
	public void setDockerfivegcoreversion(String dockerfivegcoreversion) {
		this.dockerfivegcoreversion = dockerfivegcoreversion;
	}
	public String getInternetNetworking() {
		return internetNetworking;
	}
	public void setInternetNetworking(String internetNetworking) {
		this.internetNetworking = internetNetworking;
	}
	public String getLocalControllerClientId() {
		return localControllerClientId;
	}
	public void setLocalControllerClientId(String localControllerClientId) {
		this.localControllerClientId = localControllerClientId;
	}
	
	
	public NetworkConfiguration(String n2Ip, String n3Ip, String gatewayIp, String dockerfivegcoreversion,
			String localControllerClientId) {
		super();
		this.n2Ip = n2Ip;
		this.n3Ip = n3Ip;
		GatewayIp = gatewayIp;
		this.dockerfivegcoreversion = dockerfivegcoreversion;
		this.localControllerClientId = localControllerClientId;
	}
	public NetworkConfiguration() {
		super();
		// TODO Auto-generated constructor stub
	}
	public NetworkConfiguration(String n2Ip, String n3Ip, String gatewayIp, String dockerfivegcoreversion,
			String internetNetworking, String localControllerClientId) {
		super();
		this.n2Ip = n2Ip;
		this.n3Ip = n3Ip;
		GatewayIp = gatewayIp;
		this.dockerfivegcoreversion = dockerfivegcoreversion;
		this.internetNetworking = internetNetworking;
		this.localControllerClientId = localControllerClientId;
	}
	

	
	
	
	

}
