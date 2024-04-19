package com.example.creatingcontainer.Service.Impl;

 
 
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.creatingcontainer.Model.InternalDataModel;
 
import com.example.creatingcontainer.Repository.InternalDataRepository;
 
import com.example.creatingcontainer.Service.InitializationService;
import com.example.creatingcontainer.Service.InternalDataService;

 

 

@Service
public class InitializationServiceImpl implements InitializationService {

	@Autowired
	InternalDataRepository internalDataRepository;

	@Autowired
	InternalDataService internalDataService;

	 

	@Override
	public boolean initialize() {
		if (internalDataRepository.count() == 0) {
//			Generate a very strong id for the device.
			String characters = "sddjifeuriojgfnxanmsnzomzfnoefosefscdefghijklmnopqrstuvwxyz0123456789";
			String deploymentId = "nua-" + RandomStringUtils.random(22, characters);
            InternalDataModel idm = new InternalDataModel(deploymentId);
			idm.setDeploymentId(deploymentId);
			internalDataRepository.save(idm);
			return true;
		}
		return false;
	}
	

	}

 
