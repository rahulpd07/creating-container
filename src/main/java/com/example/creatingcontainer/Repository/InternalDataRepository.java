package com.example.creatingcontainer.Repository;

 

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.creatingcontainer.Dto.DockerIPandDockerPort;
import com.example.creatingcontainer.Model.InternalDataModel;

import jakarta.transaction.Transactional;

 

@EnableJpaRepositories
public interface InternalDataRepository extends JpaRepository<InternalDataModel, Long> {
  
	@Query("SELECT deploymentId from InternalDataModel")
	public String getClientId();
		
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE InternalDataModel i SET i.sdnIp = ?2, i.sdnPort = ?3, i.five5GcoreIp = ?4, i.fiveGCorePort = ?5, i.dockerIp = ?6, i.dockerPort= ?7, i.tenantId = ?8, i.globalControllerIp = ?9, i.globalControllerPort= ?10  WHERE i.id = ?1")
	public void updateInernalData(long l,String sdnIp, String sdnPort,String five5GcoreIp, String fiveGCorePort,String dockerIp,String dockerPort,String tenantId,
			String globalControllerIp,String globalControllerPort);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE InternalDataModel i SET i.dockerIp = ?1, i.dockerPort =?2, i.globalControllerIp=?4, i.globalControllerPort=?5 WHERE i.deploymentId = ?3")
	public void updatedockerIpandPort(String dockerIp, String dockerPort,String deploymentId,String globalControllerIp,String globalControllerPort);
   
	@Query("SELECT dockerIp, dockerPort from InternalDataModel")
	public List<Object[]> returnIpandPort();
	
	
	@Query("SELECT deploymentId FROM InternalDataModel")
	public String searchThecontrollerClientId();
	

}
