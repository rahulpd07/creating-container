package com.example.creatingcontainer.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.creatingcontainer.Model.NetworkConfiguration;
import com.example.creatingcontainer.Service.fiveGcore.DockerNetworkConfiguration;
 

 

@RestController
@CrossOrigin
@RequestMapping("/5gcore")
public class NetworkConfigurationFrontendController {

	@Autowired
	DockerNetworkConfiguration  configuration;
	
	@GetMapping("/v1/5gconfiguration/networkconfiguration")
	public List<NetworkConfiguration> returnAllNetworkConfiguration() {
		return configuration.returnnetworkConfiguration();
	}
	
	@PostMapping("/v1/5gconfiguration/networkconfiguration/netwoking={networking}")
	public void changeAllNetworkConfiguration(@RequestBody NetworkConfiguration networkConfiguration,@PathVariable String networking) {
		configuration.storenetworkConfiguration(networkConfiguration,networking);
	}
	 

}
