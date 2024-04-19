package com.example.creatingcontainer.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.example.creatingcontainer.Dto.DockerIPandDockerPort;
import com.example.creatingcontainer.Model.DockerContainerNames;
import com.example.creatingcontainer.Model.DockerContainerResponse;
import com.example.creatingcontainer.Model.DockerImageNames;
import com.example.creatingcontainer.Model.Dto;
import com.example.creatingcontainer.Model.PorductUpdateInfo;
import com.example.creatingcontainer.Repository.InternalDataRepository;
import com.example.creatingcontainer.Repository.PorductUpdateInfoRepository;
import com.example.creatingcontainer.Service.Impl.TenantDetailsSyncToGlobalControllerImpl;
 

 

 

 
 
@Component
@Service
public class ServiceImpl extends Thread implements ServiceInterface {
	
	 @Autowired
		InternalDataRepository internalDataRepository;
  @Autowired
	PorductUpdateInfoRepository  porductUpdateInfoRepository;

  private static final Logger logger = LoggerFactory.getLogger(ServiceImpl.class);

  
	private final Object lock = new Object();
	
	   @Value("${server.ipAddress}")
	    private  String  backendIpAddress;
	    @Value("${name.docker.network}")
	    private String nameofdockerNetwork;
	    @Value("${mysql.version}")
	    private String mysqlImgVersion;
	    @Value("${mongo.version}")
	    private String mongoImgVersion;
	    @Value("${backend.version}")
	    private String backendImgVersion;
	    @Value("${frontend.version}")
	    private String frontendImgVersion;
	    @Value("${fiveG.version}")
	    private String fiveGv;

	    
		private String vmPathNameConfiguration;
		@Value("${vm.pathName.configuration}")
		public void setVmpathNameConfiguration(String vmPathNameConfiguration) {
			this.vmPathNameConfiguration = vmPathNameConfiguration;
		}  
	    
	   // for version update  
	    
//	    public void update()
//	    {
//	    
//	    WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080/").build();
//	    String str = webClient.get().uri("/api/v1/getit").retrieve().bodyToMono(String.class).block();
//	    versionInterface.updateV(str,1);
//	    }
	    public DockerIPandDockerPort setdockerIpAndPort()
	    {
	    	DockerIPandDockerPort model = new DockerIPandDockerPort();
	        List<Object[]> result=	internalDataRepository.returnIpandPort();
		    for (Object[] row : result) {
			 model.setDocker_ip((String) row[0]);
	         model.setDocker_port((String) row[1]);
	         
		    }
	         return model;
		 }

// Do something with dockerIp and dockerPort
	     
	    
 
	@Override
	public Map<String,String> saveData() {
	 DockerIPandDockerPort dockerIPandDockerPort = this.setdockerIpAndPort();
//		System.out.println("1111111111111111111111111---------" + dockerIPandDockerPort.docker_ip);
		WebClient webClient = WebClient.create();
    	List<Dto> getContainers = 
    			webClient.get()
                .uri("http://"+ dockerIPandDockerPort.docker_ip+ ":"+ dockerIPandDockerPort.docker_port+ "/v1.44/containers/json?limit=100")
                .retrieve()
                .bodyToFlux(Dto.class)
                .collectList()
                .block();
    	
    //http://localhost:2375/v1.43/containers/json?limit=100	
    	
    	List<List<Dto>>myList = new ArrayList();
    	myList.add(getContainers);
     
     

    	Map<String,String>h = new HashMap<>();
    	for (List<Dto> list : myList) {
    		Dto model = new Dto();
    		 for(Dto dto : list)
    		 {
    				
    			 String s = "";
   			  List<String>n = dto.getNames();
   			    for(String names : n)
   			    {
   			     s = names.substring(1, names.length());
//   			     pName.add(s);
   			   
   			    }
   				  
   				model.setState(dto.getState());
   				h.put(s,model.getState());
   			
    		 }
    		 
		
			
		}
 
    return h;
}
	
	 

	public PorductUpdateInfo  getProductVersionFromDb(String productName)
	 {
		 PorductUpdateInfo porductUpdateInfo = porductUpdateInfoRepository.findByProductName(productName);
		  return porductUpdateInfo;
	 }
	 

	public DockerContainerNames containerData() {
	 DockerContainerNames containerNames = new DockerContainerNames();
	 
//		containerNames.setMongo("niralos-mongo");
		containerNames.setMysql("niralos-mysql");
	    containerNames.setFrontend("niralos-sdn");
	    return containerNames;
	}
	
	public DockerImageNames GlobalImage() {
		DockerImageNames imageNames  = new DockerImageNames();
		imageNames.setMysqlName("mysql");
		imageNames.setBackendName("niralnetworks/niralos-local-sdn");		 
	    imageNames.setFrontName("niralnetworks/niralos-local-sdn");
	
		return imageNames;}

	public WebClient Webclient() {
		WebClient webClient = WebClient.builder().baseUrl("http://"+setdockerIpAndPort().docker_ip+":"+setdockerIpAndPort().docker_port+"/").build();
		return webClient; 
		}
	
	public void pullImageWebclient(String endpoint) {
		 
		this.Webclient().post().uri(endpoint).retrieve().bodyToMono(String.class)
		.doOnSubscribe(
				subscription -> logger.info("Image pull for '" + GlobalImage() + "' in progress..."))
		.doOnTerminate(() -> {
			logger.info("Image pull complete.");
			synchronized (lock) {
				lock.notify();  
			}
		}).subscribe(responseBody -> {
			// Process the response body if needed
		}, throwable -> {
			logger.info("Error: " + throwable.getMessage());
		});}
	public void restartContainer(String startEndpoint) {
		this.Webclient().post().uri(startEndpoint).retrieve().toBodilessEntity()
		.subscribe(startResponseBody -> {
			logger.info("Container start response: " + startResponseBody);
		}, throwable -> {
			if (throwable instanceof WebClientResponseException) {
				WebClientResponseException responseException = (WebClientResponseException) throwable;
				logger.error(
						"Error response body: " + responseException.getResponseBodyAsString());
			}
			logger.error("Error: " + throwable.getMessage());
		});
	}
 
 
   
	
	@Override
	public void pullMysql() {
		ServiceImpl ti = new ServiceImpl();
	    String v1 = "8.1.0";
		try {
			String imageName = this.GlobalImage().getMysqlName()+":"+v1;
		    String containerName = this.containerData().getMysql();

		  String endpoint = "/v1.44/images/create?fromImage=" + imageName;
		  synchronized (ti) {
	          this.Webclient().post().uri(endpoint)
	                  .retrieve()
	                  .bodyToMono(String.class)
	                  .doOnSubscribe(subscription -> logger.info("Image pull for '" + "mysql:8.1.0" + "' in progress..."))
	                  .doOnTerminate(() -> {
	                	  logger.info("Image pull complete.");
	                      synchronized (lock) {
	                          lock.notify();  
	                      }
	                  })
	                  .subscribe(responseBody -> {
	                     
	                  }, throwable -> {
	                	  logger.error("Error: " + throwable.getMessage());
	                  });
	      }
		  logger.info("Waiting for condition...complete");
		  
		  synchronized (lock) {
	          try {
	              lock.wait();  
		  String createEndpoint = "/containers/create?name=" + containerName;
  
		  
		  
		  Map<String, Object> jsonData = new HashMap<>();
	   
	      jsonData.put("Image", "mysql:8.1.0");
	 
	      List<String> envList = new ArrayList<>();
	      envList.add("MYSQL_ROOT_PASSWORD=root");
	      envList.add("MYSQL_DATABASE=backend_db");
	      jsonData.put("Env", envList);
	  
	      Map<String, Object> hostConfig = new HashMap<>();
	      Map<String, List<Map<String, String>>> portBindings = new HashMap<>();
	      List<Map<String, String>> portList = new ArrayList<>();
	      Map<String, String> portMap = new HashMap<>();
	      portMap.put("HostPort", "3306");
	      portList.add(portMap);
	      portBindings.put("3306/tcp", portList);
	      hostConfig.put("PortBindings", portBindings);
	      jsonData.put("HostConfig", hostConfig);
	      List<String> volumes = new ArrayList<>();
			volumes.add("/home/"+vmPathNameConfiguration+"/niral_config/db/mysql:/var/lib/mysql");
			
			hostConfig.put("Volumes", volumes);
			jsonData.put("HostConfig", hostConfig);
			  List<String> binds = new ArrayList<>();
	      hostConfig.put("Binds", binds);
			hostConfig.put("NetworkMode",  nameofdockerNetwork);
			// Add HostConfig to the main request
			jsonData.put("HostConfig", hostConfig);
 
	      jsonData.put("Name", "mysql-container");
	      DockerContainerResponse dockerContainerResponse = this.Webclient().
	    	       post().uri(createEndpoint)
	    	      .bodyValue(jsonData)
	    	      .retrieve()
	    	      .bodyToMono(DockerContainerResponse.class).block();
	    	     
	    	      
	      logger.info("Image container complete");

	    			String containerId =  dockerContainerResponse.getId();
	    			if (containerId != null) {
	    			 String startEndpoint = "/v1.44/containers/"+dockerContainerResponse.getId()+"/restart";
 

	    			 this.Webclient()
	    			 .post().uri(startEndpoint)
	    			 .retrieve()
	    			 .toBodilessEntity()
	    			 .subscribe(startResponseBody -> {
	    				 logger.info("Container start response: " + startResponseBody);
	    			 }, throwable -> {
	    			     if (throwable instanceof WebClientResponseException) {
	    			         WebClientResponseException responseException = (WebClientResponseException) throwable;
	    			         logger.error("Error response body: " + responseException.getResponseBodyAsString());
	    			     }
	    			     logger.error("Error: " + throwable.getMessage());
	    			 });
	    			}
	          } catch (InterruptedException e) {
	              e.printStackTrace();
	          }
	          logger.info("wait is finish now it's time to rock");
	      }
	      
	}catch (Exception e) {
		logger.error("the exception is "+e); 
		
	}

}


	@Override
	public void pullNiralosSdnController() {
		ServiceImpl ti = new ServiceImpl();
		Map<String,String>h1 = new HashMap<>();
		String productName = "niralos-local-sdn";
		PorductUpdateInfo porductUpdateInfo = getProductVersionFromDb(productName);
		String productVersion = porductUpdateInfo.getProductVersion();
		
//		LocalDateTime updateTime = porductUpdateInfo.getProduct_scheduled_update_dateTime();
		String imageName=this.GlobalImage().getBackendName()+":"+productVersion; 
        String containerName = this.containerData().getFrontend();
		
       
        	try {
   			 

    			String endpoint = "/v1.44/images/create?fromImage=" + imageName;
    			synchronized (ti) {
    				this.pullImageWebclient(endpoint);
    			}
    			logger.info("Waiting for condition...complete");

    			synchronized (lock) {
    				try {
    					lock.wait(); // Wait for the notification from the other thread (ti)

    					// Creating the containercontainerData
    					String createEndpoint = "/containers/create?name=" + containerName;
    					Map<String, Object> jsonData = new HashMap<>();
    					// Image
    					jsonData.put("Image", imageName);
    					Map<String, Object> hostConfig = new HashMap<>();
    				 
    			 
    					// Add HostConfig to the main request 				
    			        jsonData.put("HostConfig", hostConfig);
    					List<String> binds = new ArrayList<>();
    					binds.add("/home/"+vmPathNameConfiguration+"/niral_config:/niral_config");
    					binds.add("/home/"+vmPathNameConfiguration+"/niral_config/configuration/niralos-sdn/env.js:/binary/build/env.js");
    					hostConfig.put("Binds", binds);
    					hostConfig.put("NetworkMode",  nameofdockerNetwork);
    					// Add HostConfig to the main request
    					jsonData.put("HostConfig", hostConfig);
    					
    					
                          					
    					Map<String, List<Map<String, String>>> portBindings = new HashMap<>();

    			        // Port 8082 mapping
    			        List<Map<String, String>> portList8082 = new ArrayList<>();
    			        Map<String, String> portMap8082 = new HashMap<>();
    			        portMap8082.put("HostPort", "8082");
    			        portList8082.add(portMap8082);
    			        portBindings.put("8082/tcp", portList8082);

    			        // Port 3000 mapping
    			        List<Map<String, String>> portList3000 = new ArrayList<>();
    			        Map<String, String> portMap3000 = new HashMap<>();
    			        portMap3000.put("HostPort", "80");
    			        portList3000.add(portMap3000);
    			        portBindings.put("3000/tcp", portList3000);
    			        hostConfig.put("PortBindings", portBindings);
  				        jsonData.put("HostConfig", hostConfig);
    					// RestartPolicy
    				      
                         
    				    
    					Map<String, String> restartPolicy = new HashMap<>();
    					restartPolicy.put("Name", "always");
    					hostConfig.put("RestartPolicy", restartPolicy);
    					jsonData.put("HostConfig", hostConfig);

     

    					
    					Map<String, Object> niralosMeshNetwork = new HashMap<>();
                        niralosMeshNetwork.put("IPAMConfig", null);
                        niralosMeshNetwork.put("Links", null);
                        // Add other properties as needed

                        Map<String, Object> networks = new HashMap<>();
                        networks.put("niralos-mesh", niralosMeshNetwork);

                        // Create a HostConfig HashMap and add Networks property
                        hostConfig.put("Networks", networks);

                        // Add networks to the container creation config
                        jsonData.put("NetworkingConfig", Map.of("EndpointsConfig", networks));
                        // Add networks to the container creation config
//                        jsonData.put("NetworkingConfig", Map.of("EndpointsConfig", networks));
                      
    					DockerContainerResponse dockerContainerResponse = this.Webclient().post().uri(createEndpoint)
    							.bodyValue(jsonData).retrieve().bodyToMono(DockerContainerResponse.class).block();

    					logger.info("Image container complete");
    					String containerId = dockerContainerResponse.getId();
    					h1 = saveData();
    					String s = "running";
    					String s1 = h1.get("niralos-mysql");
     					if ((containerId != null) && (s.equals(s1))) {
    					
    						String startEndpoint = "/v1.44/containers/" + dockerContainerResponse.getId() + "/restart";
////    				 String startUrl = dockerApiUrl + startEndpoint;
    						this.restartContainer(startEndpoint);
    					}
    					else
    					{
    						logger.info("it depends on another containers");
    					}

    				} catch (Exception e) {
    					// TODO: handle exception
    				}
    			}

    		} catch (Exception e) {
    			// TODO: handle exception
    		} 
    		
        }
        
	@Override
	public void deleteContainer() {
		 
		 
//		WebClient webClient = WebClient.builder().baseUrl("http://localhost:2375").build();
		  List<HashMap<String, Object>> startResponseBody = this.Webclient().get()
	                .uri("/v1.44/containers/json")
	                .accept(MediaType.APPLICATION_JSON)
	                .retrieve()
	                .bodyToMono(new ParameterizedTypeReference<List<HashMap<String, Object>>>() {})
	                .block();

		 
		  if(startResponseBody != null) {	   
			  List<String>str=new ArrayList<>();
			  str.add("niralnetworks/niralos-kafka:v-2.2");
			  str.add("quay.io/keycloak/keycloak:23.0.3");
			  str.add("mysql:8.2.0");
			  str.add("mongo:4.4.2");
			  str.add("niralnetworks/niralos-sdn-controller:backend-test");
			  str.add("niralnetworks/niralos-sdn-controller:backend-spice-test");
			  str.add("niralnetworks/niralos-sdn-controller:frontend-test");
			  str.add("niralnetworks/niralos-sdn-controller:v-2.2.1_log-fix");
			  
	            for (HashMap<String, Object> containerInfo1 : startResponseBody) {
	                 
	            	logger.info("Container ID: " + containerInfo1.get("Id"));
	            	logger.info("Image name: " + containerInfo1.get("Image"));
	            	logger.info("Container name: " + containerInfo1.get("Names"));
//	             str.add((String) containerInfo1.get("Image"));    

	             // Add additional processing as needed
	                String Images=containerInfo1.get("Image").toString();
	               
	                for (String string : str) {
	                	if(Images.equals(string)) { 
	    	                this.Webclient().post()
	    	                .uri("/v1.44/containers/"+containerInfo1.get("Id")+"/stop")
	    	                .retrieve()
	    	                .toBodilessEntity()
	    	                .block();

	    	                logger.info("Container stopped successfully: " + containerInfo1.get("Id"));
	    	                
	    	                this.Webclient().delete()
	    	                .uri("/v1.44/containers/"+containerInfo1.get("Id"))
	    	                .retrieve()
	    	                .toBodilessEntity()
	    	                .block();

	    	   
	    	        
					}
	                
	        
	                }  }
	        

	}}


	@Override
	public void deleteByContainerName() {
		
		WebClient webClient = WebClient.create();
	 	List<Dto> getContainers = webClient.get()
                .uri("http://"+setdockerIpAndPort().docker_ip+":"+setdockerIpAndPort().docker_port+"/v1.44/containers/json?limit=100")
                .retrieve()
                .bodyToFlux(Dto.class)
                .collectList()
                .block();
   	
    	List<List<Dto>>myList = new ArrayList();
    	myList.add(getContainers);
    	
    	Map<String,String>h = new HashMap<>();
    	for (List<Dto> list : myList) {
    		Dto model = new Dto();
    		 for(Dto dto : list)
    		 {
    		  String s = "";
   			  List<String>n = dto.getNames();
   			    for(String names : n)
   			    {
   			     s = names.substring(1, names.length());
//   			     pName.add(s);
   			    System.out.println(s);
   			    }
   				  
   				model.setId(dto.getId());
   				h.put(s,model.getId());
   			
    		 }
    		 
		
			
		}
    	String c4 = "niralos-mysql";
//    	String c2 = "niralos-mongo";
//    	String c3 = "niralos-sdn-5g-microservice";
    	String c1 = "niralos-sdn";
    	logger.info("the datas are"+h);
//    	WebClient webClient1 = WebClient.builder().baseUrl("http://localhost:2375").build();
    	while (!h.isEmpty()) {
    		 
    		 Iterator<Map.Entry<String, String>> iterator = h.entrySet().iterator();
    		 while (iterator.hasNext()) {
                 Map.Entry<String, String> entry = iterator.next();
                 String containerId = entry.getValue();
                 String c = entry.getKey();
                 // Stop the container
                 logger.info(c);
               if(c.equals(c1) || c.equals(c4))
               {
            	   this.Webclient().post()
                   .uri("/v1.44/containers/" + containerId + "/stop")
                   .retrieve()
                   .toBodilessEntity()
                   .block();

            	   logger.info("Container stopped successfully: " + containerId);

           // Delete the container
           this.Webclient().delete()
                   .uri("/v1.44/containers/" + containerId)
                   .retrieve()
                   .toBodilessEntity()
                   .block();

           logger.info("Container deleted successfully: " + containerId);

           // Remove the entry from the HashMap using the iterator
           iterator.remove();

           logger.info("Removed entry from HashMap: " + entry.getKey()); 
               }
              
               else
               {
            	   logger.info("just ignored!!!!");
            	   iterator.remove();
               }
             }
    	}
 
	}	 		
		
	}
	
 
		 
 
	

	 
//public ImageNamesOnly imageOnly()
//{
//	String str = "niralnetworks/niralos-sdn-controller:backend-test";
//	int colonIndex = str.indexOf(':');
//
//   
//    int lastSlashIndex = str.lastIndexOf('/');
//    ImageNamesOnly img = new ImageNamesOnly();
//  
//    if (colonIndex != -1 && lastSlashIndex != -1) {
//        
//        String repositoryName = str.substring(lastSlashIndex + 1, colonIndex);
//        System.out.println(repositoryName);
//     
//		img.setBname(repositoryName);
//    }
//    
	

  
//	return img;
//}

//List<ImagesVersion> v =versionInterface.findAll();
//ImagesVersion v1 = new ImagesVersion();
//for (ImagesVersion imagesVersion : v) {
//	v1.setId(imagesVersion.getId());
//	v1.setBackendVersion(imagesVersion.getBackendVersion());
//	v1.setFrontendVersion(imagesVersion.getFrontendVersion());
//	v1.setMongoVersion(imagesVersion.getMongoVersion());
//	v1.setMySqlVersion(imagesVersion.getMySqlVersion());
//	v1.setUniqueNumber(imagesVersion.getUniqueNumber());
//	v1.setFiveGversion(imagesVersion.getFiveGversion());
//}
//return v1;
//}



//@Override
//public void pullMongo() {
//	ServiceImpl ti = new ServiceImpl(); 
//	String v = "4.2.1";
//	try {
//  	  	WebClient webClient = WebClient.builder().baseUrl("http://localhost:2375").build();
//		String imageName =  this.GlobalImage().getMongoName()+":"+ v;
//	 
//	    String containerName = this.containerData().getMongo();
//      
//	    
//	    
//	  String endpoint = "/v1.43/images/create?fromImage=" + imageName;
//	  synchronized (ti) {
//          this.Webclient().post().uri(endpoint)
//                  .retrieve()
//                  .bodyToMono(String.class)
//                  .doOnSubscribe(subscription -> System.out.println("Image pull for '" + imageName + "' in progress..."))
//                  .doOnTerminate(() -> {
//                      System.out.println("Image pull complete.");
//                      synchronized (lock) {
//                          lock.notify(); // Notify the waiting thread (ti2)
//                      }
//                  })
//                  .subscribe(responseBody -> {
//                      // Process the response body if needed
//                  }, throwable -> {
//                      System.err.println("Error: " + throwable.getMessage());
//                  });
//	  
//	  }
//	  System.out.println("Waiting for condition...complete");
//	  
//	  synchronized (lock) {
//          try {
//              lock.wait(); // Wait for the notification from the other thread (ti)
//	    //Creating the container
//	  String createEndpoint = "/containers/create?name=" + containerName;
////		    String createUrl = dockerApiUrl + createEndpoint; 
//
//
//      Map<String, Object> requestBodyMap = new HashMap<>();
//      requestBodyMap.put("Image", imageName);
//	      requestBodyMap.put("Env", new String[]{"MONGO_INITDB_ROOT_USERNAME=admin", "MONGO_INITDB_ROOT_PASSWORD=adminpass"});
//      requestBodyMap.put("ExposedPorts", Map.of("27017/tcp", Map.of()));
//      Map<String, Object> hostConfig = new HashMap<>();
//      hostConfig.put("PortBindings", Map.of("27017/tcp", List.of(Map.of("HostPort", "27017"))));
//      
//      List<String> binds = new ArrayList<>();
//		hostConfig.put("Binds", binds);
//		binds.add("/home/rahul/mongo/data:/data/db");
//		
//		hostConfig.put("NetworkMode",  nameofdockerNetwork);
//		// Add HostConfig to the main request
//		requestBodyMap.put("HostConfig", hostConfig);
//
//		// RestartPolicy
//		Map<String, String> restartPolicy = new HashMap<>();
//		restartPolicy.put("Name", "always");
//		hostConfig.put("RestartPolicy", restartPolicy);
//		requestBodyMap.put("HostConfig", hostConfig);
//
//		// Network Configuration
//
//		// Create a networks HashMap and add niralos-mesh property (similar to your
//		// provided NetworkSettings)
//		Map<String, Object>  dockerNetwork  = new HashMap<>();
//		dockerNetwork .put("IPAMConfig", null);
//		dockerNetwork .put("Links", null);
//		// Add other properties as needed
//
//		Map<String, Object> networks = new HashMap<>();
//		networks.put( nameofdockerNetwork,  dockerNetwork);
//
//		// Create a HostConfig HashMap and add Networks property
//		hostConfig.put("Networks", networks);
//
//		// Add networks to the container creation config
//		requestBodyMap.put("NetworkingConfig", Map.of("EndpointsConfig", networks));
//      
//		 requestBodyMap.put("HostConfig", hostConfig);
//		  
//	      DockerContainerResponse dockerContainerResponse=  this.Webclient().post()
//	      .uri(createEndpoint)
//	      .bodyValue(requestBodyMap)
//	      .retrieve()
//	      .bodyToMono(DockerContainerResponse.class)
//	      .doOnSuccess(response -> System.out.println("Container created: " + response))
//	      .block(); // blocking for simplicity, consider using subscribe() in a real application
//			
//		  
//		  System.out.println("Image container complete");
//
//			String containerId =  dockerContainerResponse.getId();
//			if (containerId != null) {
//			 String startEndpoint = "/v1.43/containers/"+dockerContainerResponse.getId()+"/restart";
////			 String startUrl = dockerApiUrl + startEndpoint;
//
//			 this.Webclient()
//			 .post().uri(startEndpoint)
//			 .retrieve()
//			 .toBodilessEntity()
//			 .subscribe(startResponseBody -> {
//			     System.out.println("Container start response: " + startResponseBody);
//			 }, throwable -> {
//			     if (throwable instanceof WebClientResponseException) {
//			         WebClientResponseException responseException = (WebClientResponseException) throwable;
//			         System.err.println("Error response body: " + responseException.getResponseBodyAsString());
//			     }
//			     System.err.println("Error: " + throwable.getMessage());
//			 });
//			}
//	        } catch (InterruptedException e) {
//	            e.printStackTrace();
//	        }
//	        System.out.println("wait is finish now it's time to rock");
//	    }
//	    
//	}catch (Exception e) {
//		System.out.println(e);
//	}
//}





//@Override
//public void sdnControllerFrontend() {
//	Map<String,String>h1 = new HashMap<>();
//	String productName = "niralos-local-sdn";
//
//	PorductUpdateInfo porductUpdateInfo = getProductVersionFromDb(productName);
//	String productVersion = porductUpdateInfo.getProductVersion();
//	String imageName = this.GlobalImage().getFrontName()+":"+productVersion;
//		String containerName = this.containerData().getFrontend();
//		ServiceImpl ti = new ServiceImpl(); 
//	
//	try {
//////		 
//
//		String endpoint = "/v1.43/images/create?fromImage=" + imageName;
//		synchronized (ti) {
//			this.pullImageWebclient(endpoint);
//		}
//		System.out.println("Waiting for condition...complete");
//
//		synchronized (lock) {
//			try {
//				lock.wait(); // Wait for the notification from the other thread (ti)
//
//				// Creating the container
//				String createEndpoint = "/containers/create?name=" + containerName;
//				Map<String, Object> jsonData = new HashMap<>();
//				// Image
//				jsonData.put("Image", imageName);
////
//////				 Cmd
//				List<String> cmdList = Arrays.asList("/bin/sh", "-c", "npm start");
//				jsonData.put("Cmd", cmdList);
//
//				// HostConfig
//				Map<String, Object> hostConfig = new HashMap<>();
//			 
//		        jsonData.put("HostConfig", hostConfig);
//			 
////		        String backendIp="192.168.0.129";
//		    	Map<String, String> environmentVariables = new HashMap<>();
//		    	environmentVariables.put("REACT_APP_BACKEND_IP", "http://"+backendIpAddress+":8083");
//		    	environmentVariables.put("HOST", "0.0.0.0");
//		    	hostConfig.put("Env", environmentVariables);
//		        jsonData.put("HostConfig", hostConfig);
//			 
//				hostConfig.put("NetworkMode",  nameofdockerNetwork);
//		 
////				hostConfig.put("NetworkMode", "niralos-mesh");
//				// Add HostConfig to the main request
//				jsonData.put("HostConfig", hostConfig);
//                  
//				 Map<String, List<Map<String, String>>> portBindings = new HashMap<>();
//			      List<Map<String, String>> portList = new ArrayList<>();
//			      Map<String, String> portMap = new HashMap<>();
//			      portMap.put("HostPort", "3000");
//			      portList.add(portMap);
//			      portBindings.put("3000/tcp", portList);
//			      hostConfig.put("PortBindings", portBindings);
//			      jsonData.put("HostConfig", hostConfig);
//				// RestartPolicy
//			      
//			   
//
//
//			 
//				Map<String, String> restartPolicy = new HashMap<>();
//				restartPolicy.put("Name", "always");
//				hostConfig.put("RestartPolicy", restartPolicy);
//				jsonData.put("HostConfig", hostConfig);
//
//				// Network Configuration
//
//				// Create a networks HashMap and add niralos-mesh property (similar to your
//				// provided NetworkSettings)
//				Map<String, Object>  dockerNetwork = new HashMap<>();
////				niralosMeshNetwork.put("IPAMConfig", null);
////				niralosMeshNetwork.put("Links", null);s
//				// Add other properties as needed
//
//				Map<String, Object> networks = new HashMap<>();
//				networks.put( nameofdockerNetwork,  dockerNetwork);
//
//				// Create a HostConfig HashMap and add Networks property
//				hostConfig.put("Networks", networks);
//
//				// Add networks to the container creation config
//				jsonData.put("NetworkingConfig", Map.of("EndpointsConfig", networks));
//               
//				DockerContainerResponse dockerContainerResponse = this.Webclient().post().uri(createEndpoint)
//						.bodyValue(jsonData).retrieve().bodyToMono(DockerContainerResponse.class).block();
//
//				System.out.println("Image container complete");
//				h1 = saveData();
//				String s = "running";
//				String s1 = h1.get("niralos-mysql");
////				String s2 = h1.get("niralos-mongo");
//				String s3 = h1.get("niralos-sdn-5g-microservice");
//				String containerId = dockerContainerResponse.getId();
//				if ((containerId != null) && (s.equals(s1)) && (s.equals(s3))) {
//					String startEndpoint = "/v1.43/containers/" + dockerContainerResponse.getId() + "/restart";
////			 String startUrl = dockerApiUrl + startEndpoint;
//					this.restartContainer(startEndpoint);
//				}
//				else
//				{
//					System.out.println("depends on another containers");
//				}
//
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//		}
//
//	} catch (Exception e) {
//		// TODO: handle exception
//	}
//
//
//}

