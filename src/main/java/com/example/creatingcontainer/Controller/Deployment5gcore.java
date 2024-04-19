package com.example.creatingcontainer.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.creatingcontainer.Repository.InternalDataRepository;
import com.example.creatingcontainer.Repository.FivegCoreRepo.DockerNetworkConfigurationrepo;
import com.example.creatingcontainer.Service.fiveGcore.ServiceofNetworkfunctionCompose;


@RestController
@RequestMapping("/5gcoredeployment")
public class Deployment5gcore {
	 @Autowired
	 ServiceofNetworkfunctionCompose composeimpl;
		@Autowired
		DockerNetworkConfigurationrepo dockerNetworkConfigurationrepo;
		@Autowired
		InternalDataRepository internalDataRepository;
	 
	@GetMapping("/v1/deploy_fivegcore/version={version}")
	public void deploy5gcore(@PathVariable String version){
		composeimpl.functionversion(version);
		 String networking = "0";
		 composeimpl.pullImageofMongo();
		 composeimpl.niralosNrf();
		 composeimpl.niralosScp();
		 composeimpl.niralosUdr();
		 composeimpl.niralosUdm();
		 composeimpl.niralosAusf();
		 composeimpl.niralosPcf();
		 composeimpl.niralosBsf();
		 composeimpl.niralosNssf();
		 composeimpl.niralosAmf();
		 composeimpl.niralosSmf();//
		 composeimpl.niralosUpf(networking);
		 System.out.println("111111111");
		 dockerNetworkConfigurationrepo.updateNetworkingInDatabase(networking,internalDataRepository.searchThecontrollerClientId());
		 System.out.println("222222222222");
	}
	
	@DeleteMapping("/v1/deploy_fivegcore/version={version}")
	public void remove5gcore(@PathVariable String version)
	{
		composeimpl.functionversion(version);
		composeimpl.niralosDeletionofContainer();
	}
}
