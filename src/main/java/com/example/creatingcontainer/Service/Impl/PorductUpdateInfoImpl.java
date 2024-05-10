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
