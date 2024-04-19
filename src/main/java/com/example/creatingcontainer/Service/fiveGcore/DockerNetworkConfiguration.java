package com.example.creatingcontainer.Service.fiveGcore;

import java.util.List;

import com.example.creatingcontainer.Model.NetworkConfiguration;


 

public interface DockerNetworkConfiguration {

	public List<NetworkConfiguration> returnnetworkConfiguration();
	public void storenetworkConfiguration(NetworkConfiguration networkConfiguration, String networking);
	public void confiurationofDockerIp(String dockern2Ip,String dockern3Ip,String gatewayIp,String dockerVersionofFivegcore);
	
}
