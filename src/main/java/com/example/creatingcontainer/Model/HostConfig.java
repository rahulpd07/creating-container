package com.example.creatingcontainer.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HostConfig {
	@JsonProperty("NetworkMode") 
    public String networkMode;

	public HostConfig() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HostConfig(String networkMode) {
		super();
		this.networkMode = networkMode;
	}

	public String getNetworkMode() {
		return networkMode;
	}

	public void setNetworkMode(String networkMode) {
		this.networkMode = networkMode;
	}
	
}
