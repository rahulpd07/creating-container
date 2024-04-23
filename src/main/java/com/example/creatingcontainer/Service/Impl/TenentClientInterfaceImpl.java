package com.example.creatingcontainer.Service.Impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.creatingcontainer.Dto.Product;
import com.example.creatingcontainer.Dto.ProductRootDto;
import com.example.creatingcontainer.Dto.Update;
import com.example.creatingcontainer.Model.TenentClient;
import com.example.creatingcontainer.Repository.PorductUpdateInfoRepository;
import com.example.creatingcontainer.Service.InternalDataService;
import com.example.creatingcontainer.Service.ServiceImpl;
import com.example.creatingcontainer.Service.TenentClientInterface;
@Component
@EnableScheduling
@Service
public class TenentClientInterfaceImpl implements TenentClientInterface {

 
	@Autowired
	PorductUpdateInfoRepository porductUpdateInfoRepository;
	@Autowired
	InternalDataService internalDataService;
	@Autowired
	AsyncConfiguration asyncConfiguration;

	@Autowired	
	ServiceImpl serviceImpl;
    private static final Logger logger = LoggerFactory.getLogger(TenentClientInterfaceImpl.class);
    
	
//    public ArrayList<String> findUsingProductName(String product1, String product2)
//	{
//		return porductUpdateInfoRepository.findVersionsByProductName(product1, product2);
//	}
   
    
    public Map<String, String> getProductNamesAndVersions(String productName1, String productName2) {
        Map<String, String> productMap = new HashMap<>();
        
        List<Object[]> products = porductUpdateInfoRepository.findProductNamesAndVersions(productName1, productName2);
        
        for (Object[] product : products) {
            String productName = (String) product[0];
            String productVersion = (String) product[1];
            productMap.put(productName, productVersion);
        }
        
        return productMap;
    }


	@Async("asyncTaskExecutor")
	@Scheduled(initialDelay = 5000,fixedRate = 10000)
	@Override
	public  void  testing() {
		System.out.println("******************************************************************************************");

	}
	@Async("asyncTaskExecutor")
	@Scheduled(initialDelay = 5000,fixedRate = 10000)
 	@Override
    public  void  getClientDatas() {
//		System.out.println("222222 APIIIIIIIIIIIIII --------------------------------------------------------------------------------");

		String globalControllerIp = internalDataService.getGlobalControllerIp();
		String globalControllerPort = internalDataService.getGlobalControllerPort();
 		porductUpdateInfoRepository.updateProductInfoTenentUpdate(internalDataService.getTenantId(),internalDataService.getDeploymentId());
		TenentClient model = new TenentClient();

		model.setDeploymentId(internalDataService.getDeploymentId());
		model.setTenantId(internalDataService.getTenantId());
		ArrayList<String>versinAvailableInDb = new ArrayList<>();
		String product1 = "niralos-local-sdn";
		String product2 = "niralos-5g-core";
//		versinAvailableInDb.addAll(findUsingProductName(product1, product2));
		 Map<String, String> currentAvailableVersionInDb = getProductNamesAndVersions(product1, product2);
		if((globalControllerIp !=null) && (globalControllerPort != null))
		{
			 try {
	    WebClient webClient = WebClient.builder().baseUrl("http://"+globalControllerIp+":"+globalControllerPort).build();
	    ProductRootDto productRootDto = webClient.get()
	            .uri(uriBuilder -> uriBuilder
	                    .path("/v1/saveDataToSiteDetailsAndCurrentProductVersion/"+"deployment_id="+model.getDeploymentId()+"/"+"tenant_name="+model.getTenantId())
	            .build())
	            .retrieve()
	            .bodyToMono(ProductRootDto.class)
	            .block();
	    System.out.println("222222 APIIIIIIIIIIIIII 222333344444445567778909876543456789876544567876545678765");
if (productRootDto != null) {
// boolean updatesProvisionStatus = productRootDto.isProvisionStatus();
	String task = "InQueue";
	Update update = productRootDto.getUpdate();
	    List<Product> productDto= update.getProducts();
	     boolean updateAvailable = update.isUpdateAvailable();
	     
	  
	  if(updateAvailable != false)
	    {
	    	for (Product allProductDetails : productDto) {
	    	
	    		for (Map.Entry<String, String> entry : currentAvailableVersionInDb.entrySet()) {
	    			
	    			if(allProductDetails.getProductName().equals(entry.getKey()) && !(allProductDetails.getProductVersion().equals(entry.getValue())) )
	    			{
		    		 	 System.out.println("the version coming from dto is"+" "+allProductDetails.getProductVersion()+ "and current version in db is"+" "+entry.getValue());
		    		 	 porductUpdateInfoRepository.updateProductInfo(internalDataService.getDeploymentId(),internalDataService.getTenantId(),allProductDetails.getProductName(),allProductDetails.getProductVersion(), update.isScheduledUpdate(), update.getUpdateDateTime(), update.isUpdateAvailable(),task);
	    				 System.out.println("the version is updated in the db");
	    			}
	     	    	   
	    			else
	    			{
		    		 	 System.out.println("the version coming from dto is"+" "+allProductDetails.getProductVersion()+ "and current version in db is"+" "+entry.getValue());
		    		 	 System.out.println("version allready present in the db");
	    			}
	    		}
	    	    
	    	   }
	    		
	    }
	   }
 }
	catch (Exception e) {
		System.out.println("exception is called");
	    logger.info("Response : " + e); 
}
 	}

 	}
 	}


