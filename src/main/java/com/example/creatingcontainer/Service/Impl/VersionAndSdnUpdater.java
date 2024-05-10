package com.example.creatingcontainer.Service.Impl;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.creatingcontainer.Dto.TanentClientRootDto;
import com.example.creatingcontainer.Model.AllProductDetails;
import com.example.creatingcontainer.Model.PorductUpdateInfo;
import com.example.creatingcontainer.Repository.PorductUpdateInfoRepository;
import com.example.creatingcontainer.Repository.FivegCoreRepo.DockerNetworkConfigurationrepo;
import com.example.creatingcontainer.Service.InternalDataService;
import com.example.creatingcontainer.Service.ServiceInterface;
import com.example.creatingcontainer.Service.VersionAndSdnUpdaterInterface;
import com.example.creatingcontainer.Service.Impl.fiveGcoreImpl.ServiceofNetworkfunctionComposeimpl;

//@Component
@EnableScheduling
@Service
public class VersionAndSdnUpdater implements VersionAndSdnUpdaterInterface {


	@Autowired
	ServiceInterface serviceInterface;

	@Autowired
	InternalDataService internalDataService;
	@Autowired
	PorductUpdateInfoRepository porductUpdateInfoRepository;

	private static final Logger logger = LoggerFactory.getLogger(VersionAndSdnUpdater.class);

//	@Autowired
//	AsyncConfiguration asyncConfiguration;
	@Autowired
	ServiceofNetworkfunctionComposeimpl serviceofNetworkfunctionComposeimpl;

	public PorductUpdateInfo getProductVersionFromDb(String productName) {
		PorductUpdateInfo porductUpdateInfo = porductUpdateInfoRepository.findByProductName(productName);
		return porductUpdateInfo;
	}

	public void updateTheRepo(PorductUpdateInfo porductUpdateInfo, String task) {
		porductUpdateInfo.setTask(task);
		porductUpdateInfoRepository.updateProductInfo(porductUpdateInfo.getDeploymentId(), porductUpdateInfo.getTenantId(), porductUpdateInfo.getProductName(), porductUpdateInfo.getProductVersion(), porductUpdateInfo.isProduct_scheduled_update(), porductUpdateInfo.getProduct_scheduled_update_dateTime(), porductUpdateInfo.isUpdateAvailable(), porductUpdateInfo.getTask());

	}

	public void updateDateTimeScheduled(PorductUpdateInfo porductUpdateInfo, boolean dateTimeScheduled) {
		logger.info("thhhhhhhhhhhhhhh" + dateTimeScheduled);
		porductUpdateInfo.setProduct_scheduled_update(dateTimeScheduled);
		porductUpdateInfoRepository.updateProductInfo(porductUpdateInfo.getDeploymentId(), porductUpdateInfo.getTenantId(), porductUpdateInfo.getProductName(), porductUpdateInfo.getProductVersion(), porductUpdateInfo.isProduct_scheduled_update(), porductUpdateInfo.getProduct_scheduled_update_dateTime(), porductUpdateInfo.isUpdateAvailable(), porductUpdateInfo.getTask());

	}

	public void updateTheUpdateAvailable(boolean availableUpdate, String productName) {
		porductUpdateInfoRepository.updateTheAvailableUpdateInTable(availableUpdate, productName);
	}
//	@Async("asyncTaskExecutor")
	@Scheduled(initialDelay = 3000, fixedRate = 1000)
	@Override
	public void pullAndUpdateSdnController() {
		String globalControllerIp = internalDataService.getGlobalControllerIp();
		String globalControllerPortNumber = internalDataService.getGlobalControllerPort();

		ArrayList<AllProductDetails> allProductDetails = new ArrayList<>();
//		 System.out.println(globalControllerIp+" "+globalControllerPortNumber);

		PorductUpdateInfo porductUpdateInfo = getProductVersionFromDb("niralos-local-sdn");
		boolean checkUpdate = porductUpdateInfo.isProduct_scheduled_update();
//	    String version = porductUpdateInfo.getProductVersion();
		boolean updateAvailable = porductUpdateInfo.isUpdateAvailable();
		LocalDateTime inputDateTime = null;
		if (porductUpdateInfo.getProduct_scheduled_update_dateTime() != null) {
			inputDateTime = porductUpdateInfo.getProduct_scheduled_update_dateTime();
		}
		TanentClientRootDto tanentClientRootDto = new TanentClientRootDto();
		LocalDateTime currentDateTime = LocalDateTime.now().withNano(0);
//			if(inputDateTime != null) {
		if ((inputDateTime != null) && (currentDateTime.equals(inputDateTime) || (currentDateTime.isAfter(inputDateTime))) && (checkUpdate == true) && (updateAvailable != false)) {
			String task2 = "InProgress";
			updateTheRepo(porductUpdateInfo, task2);
			PorductUpdateInfo porductUpdateInfo2 = getProductVersionFromDb("niralos-local-sdn");
			tanentClientRootDto.setDeploymentId(porductUpdateInfo2.getDeploymentId());
			tanentClientRootDto.setTenantId(porductUpdateInfo2.getTenantId());
			ArrayList<AllProductDetails> allProductDetails2 = new ArrayList<>();

			AllProductDetails product = new AllProductDetails();
			product.setProductName(porductUpdateInfo2.getProductName());
			product.setProductVersion(porductUpdateInfo2.getProductVersion());
			product.setProduct_scheduled_update(porductUpdateInfo2.isProduct_scheduled_update());
			product.setProduct_scheduled_update_dateTime(porductUpdateInfo2.getProduct_scheduled_update_dateTime());
			product.setTask(porductUpdateInfo2.getTask());

			allProductDetails2.add(product);
			tanentClientRootDto.setProductDetails(allProductDetails2);

			WebClient webClient = WebClient.builder().baseUrl("http://" + globalControllerIp + ":" + globalControllerPortNumber).build();
			boolean t = webClient.post()
					.uri("/v1/saveDataToUpdateVersion")
					.bodyValue(tanentClientRootDto)
					.retrieve()
					.bodyToMono(boolean.class)
					.block();

			serviceInterface.deleteByContainerName();
			logger.info("inprogress response from API" + t);

			//Pull Mysql
			serviceInterface.pullMysql();
			serviceInterface.pullNiralosSdnController();
			String task1 = "Completed";
			PorductUpdateInfo porductUpdateInfo1 = getProductVersionFromDb("niralos-local-sdn");
			updateTheRepo(porductUpdateInfo1, task1);
			ArrayList<AllProductDetails> allProductDetails1 = new ArrayList<>();
			product.setTask(task1);
			allProductDetails1.add(product);
			tanentClientRootDto.setProductDetails(allProductDetails1);

			boolean t1 = webClient.post()
					.uri("/v1/saveDataToUpdateVersion")
					.bodyValue(tanentClientRootDto)
					.retrieve()
					.bodyToMono(boolean.class)
					.block();
			logger.info("completed response from API" + t1);
			String productName = "niralos-local-sdn";
			boolean availableUpdate = false;
			updateTheUpdateAvailable(availableUpdate, productName);   //we are updating the updateAvailable in the db from true to false

		} else if (checkUpdate == false) {
			porductUpdateInfo.setTask("InProgress");
			String task = "InProgress";
			porductUpdateInfoRepository.updateProductInfo(porductUpdateInfo.getDeploymentId(), porductUpdateInfo.getTenantId(), porductUpdateInfo.getProductName(), porductUpdateInfo.getProductVersion(), porductUpdateInfo.isProduct_scheduled_update(), porductUpdateInfo.getProduct_scheduled_update_dateTime(), porductUpdateInfo.isUpdateAvailable(), porductUpdateInfo.getTask());
			updateTheRepo(porductUpdateInfo, task);
			serviceInterface.deleteByContainerName();
			PorductUpdateInfo porductUpdateInfo2 = getProductVersionFromDb("niralos-local-sdn");
			tanentClientRootDto.setDeploymentId(porductUpdateInfo2.getDeploymentId());
			tanentClientRootDto.setTenantId(porductUpdateInfo2.getTenantId());
			ArrayList<AllProductDetails> allProductDetails2 = new ArrayList<>();

			AllProductDetails product = new AllProductDetails();
			product.setProductName(porductUpdateInfo2.getProductName());
			product.setProductVersion(porductUpdateInfo2.getProductVersion());
			product.setProduct_scheduled_update(porductUpdateInfo2.isProduct_scheduled_update());
//	  		product.setProduct_scheduled_update_dateTime(porductUpdateInfo2.getProduct_scheduled_update_dateTime());
			product.setTask(porductUpdateInfo2.getTask());
			allProductDetails2.add(product);
			tanentClientRootDto.setProductDetails(allProductDetails2);

			WebClient webClient = WebClient.builder().baseUrl("http://" + globalControllerIp + ":" + globalControllerPortNumber).build();
			boolean t = webClient.post()
					.uri("/v1/saveDataToUpdateVersion")
					.bodyValue(tanentClientRootDto)
					.retrieve()
					.bodyToMono(boolean.class)
					.block();
			logger.info("Inprogress response from API" + t);
			serviceInterface.pullMysql();
			serviceInterface.pullNiralosSdnController();
			String task1 = "Completed";
			PorductUpdateInfo porductUpdateInfo1 = getProductVersionFromDb("niralos-local-sdn");
			updateTheRepo(porductUpdateInfo1, task1);
			product.setTask(task1);

			ArrayList<AllProductDetails> allProductDetails1 = new ArrayList<>();
			allProductDetails1.add(product);

			tanentClientRootDto.setProductDetails(allProductDetails1);

			boolean t1 = webClient.post()
					.uri("/v1/saveDataToUpdateVersion")
					.bodyValue(tanentClientRootDto)
					.retrieve()
					.bodyToMono(boolean.class)
					.block();
			logger.info("Completed response from API" + t1);
			String productName = "niralos-local-sdn";
			boolean availableUpdate = false;
			updateTheUpdateAvailable(availableUpdate, productName);
			boolean dateTimeUpdate = true;
			updateDateTimeScheduled(porductUpdateInfo1, dateTimeUpdate);
//			return tanentClientRootDto;
		} else
		{
			if (inputDateTime != null)
////
			{

				if ((currentDateTime != null) && (currentDateTime.isBefore(inputDateTime))) {
					String task = "Scheduled";
					porductUpdateInfoRepository.updateProductInfo(porductUpdateInfo.getDeploymentId(), porductUpdateInfo.getTenantId(), porductUpdateInfo.getProductName(), porductUpdateInfo.getProductVersion(), porductUpdateInfo.isProduct_scheduled_update(), porductUpdateInfo.getProduct_scheduled_update_dateTime(), porductUpdateInfo.isUpdateAvailable(), porductUpdateInfo.getTask());
					updateTheRepo(porductUpdateInfo, task);

					PorductUpdateInfo porductUpdateInfo1 = getProductVersionFromDb("niralos-local-sdn");

					tanentClientRootDto.setDeploymentId(porductUpdateInfo1.getDeploymentId());
					tanentClientRootDto.setTenantId(porductUpdateInfo1.getTenantId());

					ArrayList<AllProductDetails> allProductDetails2 = new ArrayList<>();

					AllProductDetails product = new AllProductDetails();
					product.setProductName(porductUpdateInfo1.getProductName());
					product.setProductVersion(porductUpdateInfo1.getProductVersion());
					product.setProduct_scheduled_update(porductUpdateInfo1.isProduct_scheduled_update());
					product.setProduct_scheduled_update_dateTime(porductUpdateInfo1.getProduct_scheduled_update_dateTime());
					product.setTask(porductUpdateInfo1.getTask());
					allProductDetails2.add(product);

					tanentClientRootDto.setProductDetails(allProductDetails2);

//					tanentClientRootDto.setProductDetails(allProductDetails);
					logger.info("update has been scheduled will be updated shortly");

					WebClient webClient = WebClient.builder().baseUrl("http://" + globalControllerIp + ":" + globalControllerPortNumber).build();
					boolean t = webClient.post()
							.uri("/v1/saveDataToUpdateVersion")
							.bodyValue(tanentClientRootDto)
							.retrieve()
							.bodyToMono(boolean.class)
							.block();
					logger.info("Scheduled response from API" + t);
				}

			}
		}


//			return tanentClientRootDto;

	}


	@Autowired
	DockerNetworkConfigurationrepo dockerNetworkConfigurationrepo;

	public String deploymentoffivegcore() {
		String networking = dockerNetworkConfigurationrepo.fetchInternetNetworkingofFivegcore();
		;
		return networking;
	}

//	@Async("asyncTaskExecutor")
	@Scheduled(initialDelay = 3000, fixedRate = 1000)
	@Override
	public void pullAndUpdate5GCore() {

		String globalControllerIp = internalDataService.getGlobalControllerIp();
		String globalControllerPortNumber = internalDataService.getGlobalControllerPort();
		ArrayList<AllProductDetails> allProductDetails = new ArrayList<>();
		PorductUpdateInfo porductUpdateInfo = getProductVersionFromDb("niralos-5g-core");
		boolean checkUpdate = porductUpdateInfo.isProduct_scheduled_update();

		LocalDateTime inputDateTime = null;

		if (porductUpdateInfo.getProduct_scheduled_update_dateTime() != null) {
			inputDateTime = porductUpdateInfo.getProduct_scheduled_update_dateTime();
		}

		TanentClientRootDto tanentClientRootDto = new TanentClientRootDto();
		LocalDateTime currentDateTime = LocalDateTime.now().withNano(0);

		boolean updateAvailable = porductUpdateInfo.isUpdateAvailable();
//	  if(inputDateTime != null){
		if ((inputDateTime != null) && (currentDateTime.equals(inputDateTime) || (currentDateTime.isAfter(inputDateTime))) && (checkUpdate == true) && (updateAvailable != false)) {

			String task2 = "InProgress";

			updateTheRepo(porductUpdateInfo, task2);


			tanentClientRootDto.setDeploymentId(porductUpdateInfo.getDeploymentId());
			tanentClientRootDto.setTenantId(porductUpdateInfo.getTenantId());
			PorductUpdateInfo porductUpdateInfo2 = getProductVersionFromDb("niralos-5g-core");
			ArrayList<AllProductDetails> allProductDetails2 = new ArrayList<>();

			AllProductDetails product = new AllProductDetails();
			product.setProductName(porductUpdateInfo2.getProductName());
			product.setProductVersion(porductUpdateInfo2.getProductVersion());
			product.setProduct_scheduled_update(porductUpdateInfo2.isProduct_scheduled_update());
			product.setProduct_scheduled_update_dateTime(porductUpdateInfo2.getProduct_scheduled_update_dateTime());
			product.setTask(porductUpdateInfo2.getTask());

			allProductDetails2.add(product);
			tanentClientRootDto.setProductDetails(allProductDetails2);

			WebClient webClient = WebClient.builder().baseUrl("http://" + globalControllerIp + ":" + globalControllerPortNumber).build();
			boolean t = webClient.post()
					.uri("/v1/saveDataToUpdateVersion")
					.bodyValue(tanentClientRootDto)
					.retrieve()
					.bodyToMono(boolean.class)
					.block();
			serviceofNetworkfunctionComposeimpl.niralosDeletionofContainer();
			logger.info("Inprogress response from API" + t);
			logger.info("core containers deleted");
			serviceofNetworkfunctionComposeimpl.pullImageofMongo();
			serviceofNetworkfunctionComposeimpl.niralosNrf();
			serviceofNetworkfunctionComposeimpl.niralosScp();
			serviceofNetworkfunctionComposeimpl.niralosUdr();
			serviceofNetworkfunctionComposeimpl.niralosUdm();
			serviceofNetworkfunctionComposeimpl.niralosAusf();
			serviceofNetworkfunctionComposeimpl.niralosPcf();
			serviceofNetworkfunctionComposeimpl.niralosBsf();
			serviceofNetworkfunctionComposeimpl.niralosNssf();
			serviceofNetworkfunctionComposeimpl.niralosAmf();
			serviceofNetworkfunctionComposeimpl.niralosSmf();
			serviceofNetworkfunctionComposeimpl.niralosUpf(this.deploymentoffivegcore());

			String task1 = "Completed";
			PorductUpdateInfo porductUpdateInfo1 = getProductVersionFromDb("niralos-5g-core");
			updateTheRepo(porductUpdateInfo1, task1);
			ArrayList<AllProductDetails> allProductDetails1 = new ArrayList<>();
			product.setTask(task1);

			allProductDetails1.add(product);
			tanentClientRootDto.setProductDetails(allProductDetails1);
			boolean t1 = webClient.post()
					.uri("/v1/saveDataToUpdateVersion")
					.bodyValue(tanentClientRootDto)
					.retrieve()
					.bodyToMono(boolean.class)
					.block();
			logger.info("Completed response from API" + t1);
			String productName = "niralos-5g-core";
			boolean availableUpdate = false;
			updateTheUpdateAvailable(availableUpdate, productName);
//			return tanentClientRootDto;
		} else if (checkUpdate == false) {
			String task = "InProgress";
			PorductUpdateInfo porductUpdateInfo2 = getProductVersionFromDb("niralos-5g-core");
			updateTheRepo(porductUpdateInfo2, task);

			ArrayList<AllProductDetails> allProductDetails2 = new ArrayList<>();
			tanentClientRootDto.setDeploymentId(porductUpdateInfo.getDeploymentId());
			tanentClientRootDto.setTenantId(porductUpdateInfo.getTenantId());

			AllProductDetails product = new AllProductDetails();
			product.setProductName(porductUpdateInfo2.getProductName());
			product.setProductVersion(porductUpdateInfo2.getProductVersion());
			product.setProduct_scheduled_update(porductUpdateInfo2.isProduct_scheduled_update());
			product.setTask(task);
			allProductDetails2.add(product);
			tanentClientRootDto.setProductDetails(allProductDetails2);
			WebClient webClient = WebClient.builder().baseUrl("http://" + globalControllerIp + ":" + globalControllerPortNumber).build();
			boolean t = webClient.post()
					.uri("/v1/saveDataToUpdateVersion")
					.bodyValue(tanentClientRootDto)
					.retrieve()
					.bodyToMono(boolean.class)
					.block();
			logger.info("Inprogress response from API" + t);
			serviceofNetworkfunctionComposeimpl.niralosDeletionofContainer();
			logger.info("core containers deleted");

			serviceofNetworkfunctionComposeimpl.niralosNrf();
			serviceofNetworkfunctionComposeimpl.niralosScp();
			serviceofNetworkfunctionComposeimpl.niralosUdr();
			serviceofNetworkfunctionComposeimpl.niralosUdm();
			serviceofNetworkfunctionComposeimpl.niralosAusf();
			serviceofNetworkfunctionComposeimpl.niralosPcf();
			serviceofNetworkfunctionComposeimpl.niralosBsf();
			serviceofNetworkfunctionComposeimpl.niralosNssf();
			serviceofNetworkfunctionComposeimpl.niralosAmf();
			serviceofNetworkfunctionComposeimpl.niralosSmf();
			serviceofNetworkfunctionComposeimpl.niralosUpf(this.deploymentoffivegcore());

			String task1 = "Completed";
			product.setTask(task1);
			ArrayList<AllProductDetails> allProductDetails1 = new ArrayList<>();
			allProductDetails1.add(product);
			tanentClientRootDto.setProductDetails(allProductDetails1);
			PorductUpdateInfo porductUpdateInfo1 = getProductVersionFromDb("niralos-5g-core");
			updateTheRepo(porductUpdateInfo1, task1);
			boolean t1 = webClient.post()
					.uri("/v1/saveDataToUpdateVersion")
					.bodyValue(tanentClientRootDto)
					.retrieve()
					.bodyToMono(boolean.class)
					.block();
			logger.info("Completed response from API" + t1);
			boolean dateTimeUpdate = true;
			updateDateTimeScheduled(porductUpdateInfo1, dateTimeUpdate);
			String productName = "niralos-5g-core";
			boolean availableUpdate = false;
			updateTheUpdateAvailable(availableUpdate, productName);
//			return tanentClientRootDto;
		} else {
			if (inputDateTime != null) {
				if ((currentDateTime.isBefore(inputDateTime)) && (currentDateTime != null)) {
					String task = "Scheduled";
					porductUpdateInfoRepository.updateProductInfo(porductUpdateInfo.getDeploymentId(), porductUpdateInfo.getTenantId(), porductUpdateInfo.getProductName(), porductUpdateInfo.getProductVersion(), porductUpdateInfo.isProduct_scheduled_update(), porductUpdateInfo.getProduct_scheduled_update_dateTime(), porductUpdateInfo.isUpdateAvailable(), porductUpdateInfo.getTask());
					updateTheRepo(porductUpdateInfo, task);
					PorductUpdateInfo porductUpdateInfo1 = getProductVersionFromDb("niralos-5g-core");
					tanentClientRootDto.setDeploymentId(porductUpdateInfo.getDeploymentId());
					tanentClientRootDto.setTenantId(porductUpdateInfo.getTenantId());

					ArrayList<AllProductDetails> allProductDetails2 = new ArrayList<>();
					AllProductDetails product = new AllProductDetails();
					product.setProductName(porductUpdateInfo1.getProductName());
					product.setProductVersion(porductUpdateInfo1.getProductVersion());
					product.setProduct_scheduled_update(porductUpdateInfo1.isProduct_scheduled_update());
					product.setProduct_scheduled_update_dateTime(porductUpdateInfo1.getProduct_scheduled_update_dateTime());
					product.setTask(task);
					allProductDetails2.add(product);
					tanentClientRootDto.setProductDetails(allProductDetails2);
					logger.info("the task has been scheduled for update");
					WebClient webClient = WebClient.builder().baseUrl("http://" + globalControllerIp + ":" + globalControllerPortNumber).build();
					boolean t = webClient.post()
							.uri("/v1/saveDataToUpdateVersion")
							.bodyValue(tanentClientRootDto)
							.retrieve()
							.bodyToMono(boolean.class)
							.block();
					logger.info("Scheduled response from API" + t);

				}
			}

//			return tanentClientRootDto;
		}
	}
}
