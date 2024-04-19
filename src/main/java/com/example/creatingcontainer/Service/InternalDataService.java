package com.example.creatingcontainer.Service;

import com.example.creatingcontainer.Model.InternalDataModel;

//Interface
public interface InternalDataService {

	public InternalDataModel getInternalDetails();

	public void saveInternalDataModel(InternalDataModel internalData);

	public String getSdnIp();
	
	public String getSdnPort();

	public String getDeploymentId();
    
	public String getFive5GcoreIp();
	
	public String getFiveGCorePort();
	
	public String getDockerIp();
	
	public String getDockerPort();
	
	public String getTenantId();
	
	public String getGlobalControllerIp();
	
	public String getGlobalControllerPort();
 
}
