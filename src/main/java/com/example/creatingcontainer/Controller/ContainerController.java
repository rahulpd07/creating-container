package com.example.creatingcontainer.Controller;

 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.creatingcontainer.Dto.TanentClientRootDto;
import com.example.creatingcontainer.Dto.GlobalController.ReturnTenantData;
import com.example.creatingcontainer.Service.ServiceImpl;
import com.example.creatingcontainer.Service.Impl.TenantDetailsSyncToGlobalControllerImpl;
import com.example.creatingcontainer.Service.Impl.PorductUpdateInfoImpl;
import com.example.creatingcontainer.Service.Impl.TenentClientInterfaceImpl;
import com.example.creatingcontainer.Service.Impl.VersionAndSdnUpdater;
 
  

@RestController
@RequestMapping("api/v1")
public class ContainerController extends Thread{
	 
	@Autowired
	VersionAndSdnUpdater versionAndSdnUpdater;
	
	@Autowired
	private TenantDetailsSyncToGlobalControllerImpl agentModelServiceImpl;
	
	@Autowired
	private ServiceImpl serviceImpl;
	  
	@Autowired
	private TenentClientInterfaceImpl tenentClientInterfaceImpl;
	  
	@Autowired
	private PorductUpdateInfoImpl porductUpdateInfoImpl;
	  
	
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
 
    @GetMapping("/pullTheImageAndStartTheContainer")
    public void getContainer()
    {

    	 
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
