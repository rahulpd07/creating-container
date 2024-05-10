package com.example.creatingcontainer.Controller;

 
import com.example.creatingcontainer.Dto.DataEntityDto;
import com.example.creatingcontainer.Model.DataEntity;
import com.example.creatingcontainer.Model.Dto;
import com.example.creatingcontainer.Service.Impl.*;
import com.example.creatingcontainer.Service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.creatingcontainer.Dto.TanentClientRootDto;
import com.example.creatingcontainer.Dto.GlobalController.ReturnTenantData;
import com.example.creatingcontainer.Service.ServiceImpl;

import java.util.List;


@RestController
@RequestMapping("api/v1")
public class ContainerController extends Thread{

	@Autowired
	CurrentRunningVersion currentRunningVersion;
	@Autowired
	VersionAndSdnUpdater versionAndSdnUpdater;
	
	@Autowired
	private TenantDetailsSyncToGlobalControllerImpl agentModelServiceImpl;
	
	@Autowired
	private ServiceImpl serviceImpl;

	@Autowired
	private ServiceInterface serviceInterface;
	@Autowired
	private TenentClientInterfaceImpl tenentClientInterfaceImpl;
	  
	@Autowired
	private PorductUpdateInfoImpl porductUpdateInfoImpl;

	@Autowired
	DataEntityInterfaceImpl dataEntityInterfaceImpl;
	@GetMapping("/sendTenentAndDeploymentId")
	public boolean showProductDetails()	{
		return agentModelServiceImpl.saveData();
	}
	
	@GetMapping("/updateTheNewVersionToDb")
	public void productVersionUpdate(){
		 tenentClientInterfaceImpl.getClientDatas();
	}
	
	@GetMapping("/pullImages")
	public void getVarsionData(){
		 serviceImpl.pullNiralosSdnController();
	}
 
	@GetMapping("/deleteByContainerNames")
	public void deleteContainer(){
		serviceImpl.deleteByContainerName();
	}
	
	@GetMapping("/delete")
	public void stopContainer(){
		serviceImpl.deleteContainer();
	}
	
    @GetMapping("/stopUpdateAndStartTheContainers")
	public void sdnVersionUpdater(){
	   versionAndSdnUpdater.pullAndUpdateSdnController();
	}

	@GetMapping("/saveSiteDetailsToDb")
	public void getAllInfoOfSite()
	{
		dataEntityInterfaceImpl.saveAllDatasToDb();
	}
	@GetMapping("/getAllRunningContainers")
	public List<Dto> returnAllTheRunning()
	{
	  return currentRunningVersion.getRunningContainer();
	}

	@GetMapping("/getSiteInfo")
    public List<DataEntity> getAllList()
	{
		return dataEntityInterfaceImpl.getSiteInfoFromDb();
	}

	@GetMapping("/siteAndContactInfo")
	public DataEntityDto getDataEntityInfo()
	{
		return dataEntityInterfaceImpl.setDataEntityToDto();
	}
    @GetMapping("/pullTheImageAndStartTheContainer/version={version}")
    public void getContainer(@PathVariable String version)
    {
		serviceInterface.updateInitialVersionInDb(version);
    	 
    	 Thread  t1 = new Thread(() -> {
    		 
    		 serviceImpl.pullMysql();
    		 
    	 });



    //
//    	 Thread t2= new Thread(() -> {
//    		    try {
//    		        
//    		        t1.join();   
//    		        serviceImpl.pullMongo();
//    		      
//    		    } catch (InterruptedException e) {
//    		        e.printStackTrace();
//    		    }
//    		});

    		Thread t2 = new Thread(() -> {
    			
    		    try {  
    		    	  t1.join(); 
    		      
    		        serviceImpl.pullNiralosSdnController();
    		       
    		    } catch (InterruptedException e) {
    		        e.printStackTrace();
    		    }
    		});

    	 
//
//    	 Thread t3= new Thread(() -> {
//    		
//    		 try {
//    			 
//                  t2.join();
//                  serviceImpl.sdnControllerFrontend();
//                 } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//    	 });
    	 
     t1.start();
     t2.start();
   
     try {
//         // Wait for all threads to finish
          t1.join();
         t2.join();
//         t3.join();
//         
     } catch (InterruptedException e) {
         e.printStackTrace();
     }
    }
}



//@GetMapping("setAllProduct")
//public void productUpdateInfo()
//{
//	porductUpdateInfoImpl.savePorductUpdateInfo();
//}
//

//@GetMapping("/get")
//public Map<String,String> getDatas(){
//	 return serviceImpl.saveData();
//}

//@GetMapping("/imgonly")
//public ImageNamesOnly getImageOnly(){
//	return serviceImpl.imageOnly();
//}


//@GetMapping("/getversion")
//public ImagesVersion getVersions()
//{
//	return serviceImpl.globalVersion();
//}

//@GetMapping("/getroot")
//public  TanentClientRootDto  getRootData()
//{
//	 return tenentClientInterfaceImpl.getClientDatas();
//}

//@GetMapping("/getContainer")
//public void getContainer()
//{
//	 serviceImpl.pullNiralOsSdn5G();
//}
