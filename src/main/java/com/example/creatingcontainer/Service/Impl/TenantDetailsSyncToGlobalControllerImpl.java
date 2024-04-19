package com.example.creatingcontainer.Service.Impl;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.example.creatingcontainer.Dto.GlobalController.ReturnTenantData;
import com.example.creatingcontainer.Model.ProductDetails;
import com.example.creatingcontainer.Repository.PorductUpdateInfoRepository;
import com.example.creatingcontainer.Service.InternalDataService;
import com.example.creatingcontainer.Service.ServiceImpl;
import com.example.creatingcontainer.Service.TenantDetailsSyncToGlobalController;
 
@EnableScheduling
@Service
public class TenantDetailsSyncToGlobalControllerImpl implements  TenantDetailsSyncToGlobalController{

	@Autowired
	InternalDataService internalDataService;
	@Autowired
	PorductUpdateInfoRepository porductUpdateInfoRepository;
	@Autowired	
	ServiceImpl serviceImpl;

	private static final Logger logger = LoggerFactory.getLogger(TenantDetailsSyncToGlobalControllerImpl.class);
	
   @Scheduled(initialDelay = 10000, fixedRate = 10000)
	@Override
	public  boolean saveData() {
		boolean response = false;
	   String globalControllerIp=internalDataService.getGlobalControllerIp();
	   String tenantName = internalDataService.getTenantId();
	   ReturnTenantData returnTenantData = new ReturnTenantData();;
	   String globalControllerPortNumber = internalDataService.getGlobalControllerPort();
	   if((globalControllerIp != null) && (tenantName != null) && (globalControllerPortNumber != null)) {
		 
		returnTenantData.setDeploymentId(internalDataService.getDeploymentId());
		returnTenantData.setTenantId(internalDataService.getTenantId());

		List<ProductDetails> productDetailsList = porductUpdateInfoRepository.findAll()
		    .stream()
		    .map(productUpdateInfo -> {
		        ProductDetails productDetailsobject = new ProductDetails();
		        productDetailsobject.setProductName(productUpdateInfo.getProductName());   
		        productDetailsobject.setProductVersion(productUpdateInfo.getProductVersion());
		        return productDetailsobject;
		    })
		    .collect(Collectors.toList());
		returnTenantData.setProductDetails(productDetailsList);
		try {
		WebClient webClient = WebClient.builder().baseUrl("http://"+globalControllerIp+":"+globalControllerPortNumber).build();
	    response = webClient.post()
		        .uri("/v1/saveDataToSiteDetailsAndCurrentProductVersion")
		        .bodyValue(returnTenantData)
		        .retrieve()
		        .bodyToMono(Boolean.class)
		        .block();

		logger.info("Response from Global controller API:" + response);
		return response;
		}catch (Exception e) {
			logger.error("Global Controller IP is not pingable :" +e);
		}
	   }
	   else {
		   logger.warn("condition not met");
		  }
	   return response;
	    }
	
   }
 
 
//	Logger logger = null;
//	ReturnTenantData returnTenantData = new ReturnTenantData();
//	List<ProductDetails>productDetailsList = new ArrayList<>();
//	List<PorductUpdateInfo> porductUpdateInfo = porductUpdateInfoRepository.findAll();
//	returnTenantData.setDeploymentId(internalDataService.getDeploymentId());
//	returnTenantData.setTenantId(internalDataService.getTenantId());
//	 for(PorductUpdateInfo productUpdateInfo: porductUpdateInfo){
// ProductDetails productDetailsobject = new ProductDetails();
// productDetailsobject.setProductName(productUpdateInfo.getProductName());	
// productDetailsobject.setProductVersion(productUpdateInfo.getProductVersion());
// productDetailsList.add(productDetailsobject);
//	 }
//	 returnTenantData.setProductDetails(productDetailsList);
//	    WebClient webClient = WebClient.builder().baseUrl("http://192.168.0.89:8099").build();
//	   boolean response= webClient.post()
//     .uri("/v1/saveDataToSiteDetailsAndCurrentProductVersion")
//     .bodyValue(returnTenantData) 
//     .retrieve()
//     .bodyToMono(boolean.class)
//     .block(); 
//	logger.info("Response from Global controller api :"+ response);
//	  return response;
//
	
