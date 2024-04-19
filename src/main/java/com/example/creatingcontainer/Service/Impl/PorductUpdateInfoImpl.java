package com.example.creatingcontainer.Service.Impl;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.creatingcontainer.Model.PorductUpdateInfo;
import com.example.creatingcontainer.Repository.PorductUpdateInfoRepository;
import com.example.creatingcontainer.Service.InternalDataService;
import com.example.creatingcontainer.Service.PorductUpdateInfoInterface;

@Service
public class PorductUpdateInfoImpl implements PorductUpdateInfoInterface {

	@Autowired
	PorductUpdateInfoRepository porductUpdateInfoRepository;
	@Autowired
	InternalDataService internalDataService;
	
	@Value("${docker.versionof.fivegcore}")
	 public String dockerversionFivegcore;
	public void setDockerVersionFivegcore(String dockerversionFivegcore) {
		this.dockerversionFivegcore = dockerversionFivegcore;
	}
	
	@Value("${docker.version.sdn}")
	public String dockerVersionOfSdn;
	public void setDockerVersionOfSdn(String dockerVersionOfSdn) {
		this.dockerVersionOfSdn = dockerVersionOfSdn;
	}

	@Override
	public void savePorductUpdateInfo() {
// 
//		if(porductUpdateInfoRepository.countProductsUpdateInfo() <= 1) 
//		{
// 		  
//			 PorductUpdateInfo model1 = new  PorductUpdateInfo();
//			 model1.setDeploymentId(internalDataService.getDeploymentId());
//			 model1.setTenantId(internalDataService.getTenantId());
//			 model1.setProductName("UBUNTU");
//			 model1.setProductVersion("20.04");
//			 model1.setProduct_scheduled_update(false);
//			 model1.setProduct_scheduled_update_dateTime(null);
//			 model1.setTask(null);
//			 porductUpdateInfoRepository.save(model1);
//			 			 
//			 PorductUpdateInfo model3 = new  PorductUpdateInfo();
//			 model3.setDeploymentId(internalDataService.getDeploymentId());
//			 model3.setTenantId(internalDataService.getTenantId());
//			 model3.setProductName("niralos-5g-core");
//			 model3.setProductVersion("v-2.2.1_log-fix");
//			 model3.setProduct_scheduled_update(false);
//			 model3.setProduct_scheduled_update_dateTime(null);
//			 model3.setTask(null);
//			 porductUpdateInfoRepository.save(model3);
//			 
//		}	
		
		if (porductUpdateInfoRepository.countProductsUpdateInfo() <= 1) {
 		    List<PorductUpdateInfo> newProducts = Arrays.asList(
		        createProduct("niralos-local-sdn", dockerVersionOfSdn),
		        createProduct("niralos-5g-core",  dockerversionFivegcore));
 		    	porductUpdateInfoRepository.saveAll(newProducts);}
		}
			private PorductUpdateInfo createProduct(String productName, String productVersion) {
		    PorductUpdateInfo model = new PorductUpdateInfo();
 		    model.setDeploymentId(internalDataService.getDeploymentId());
		    model.setTenantId(internalDataService.getTenantId());
		    model.setProductName(productName);
		    model.setProductVersion(productVersion);
		    model.setProduct_scheduled_update(true);
		    model.setProduct_scheduled_update_dateTime(null);
		    model.setTask(null);
		    model.setUpdateAvailable(false);
 		    return model;
		}	
}
