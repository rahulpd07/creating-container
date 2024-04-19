package com.example.creatingcontainer.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.creatingcontainer.Model.InternalDataModel;
import com.example.creatingcontainer.Repository.InternalDataRepository;
import com.example.creatingcontainer.Service.InternalDataService;

 

 

@Service
public class InternalDataServiceImpl implements InternalDataService {

	@Autowired
	InternalDataRepository internalDataRepository;

	@Override
	public InternalDataModel getInternalDetails() {
	return internalDataRepository.findById(1L).orElse(null);
	}


	@Override
	public String getSdnIp() {
		 
		return internalDataRepository.findById(1L).orElse(null).getSdnIp();
	}

	@Override
	public String getSdnPort() {
	return  internalDataRepository.findById(1L).orElse(null).getSdnPort();
	}

	@Override
	public String getDeploymentId() {
	return internalDataRepository.findById(1L).orElse(null).getDeploymentId();
	}

	@Override
	public String getFive5GcoreIp() {
	return  internalDataRepository.findById(1L).orElse(null).getFive5GcoreIp();
	}

	@Override
	public String getFiveGCorePort() {
	return internalDataRepository.findById(1L).orElse(null).getFiveGCorePort();
	}

	@Override
	public String getDockerIp() {
	return internalDataRepository.findById(1L).orElse(null).getDockerIp();
	}

	@Override
	public String getDockerPort() {
	return internalDataRepository.findById(1L).orElse(null).getDockerPort();
	}

	@Override
	public String getTenantId() {
	return  internalDataRepository.findById(1L).orElse(null).getTenantId();
	}

	@Override
	public String getGlobalControllerIp() {
	return  internalDataRepository.findById(1L).orElse(null).getGlobalControllerIp();
	}

	@Override
	public String getGlobalControllerPort() {
	return  internalDataRepository.findById(1L).orElse(null).getGlobalControllerPort();
	}
	
	@Override
	public void saveInternalDataModel(InternalDataModel internalData) {
		internalDataRepository.updateInernalData(1L, internalData.getSdnIp(),internalData.getSdnPort(),internalData.getFive5GcoreIp(),internalData.getFiveGCorePort(),
		internalData.getDockerIp(),internalData.getDockerPort(),internalData.getTenantId(),internalData.getGlobalControllerIp(),internalData.getGlobalControllerPort());
		
	}

	 


}
