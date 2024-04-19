package com.example.creatingcontainer.Service;

 
import java.util.Map;

 

 

 

public interface ServiceInterface {
	public Map<String,String> saveData();
//	public void pullMongo();
	public void pullMysql();
 
	public void pullNiralosSdnController();
    
//	public void sdnControllerFrontend();
	public void deleteContainer();
	public void deleteByContainerName();
  
}
