package com.example.creatingcontainer.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NetworkSettings {

	@JsonProperty("Networks") 
    public Networks networks;

	public NetworkSettings() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NetworkSettings(Networks networks) {
		super();
		this.networks = networks;
	}

	public Networks getNetworks() {
		return networks;
	}

	public void setNetworks(Networks networks) {
		this.networks = networks;
	}
	
}
