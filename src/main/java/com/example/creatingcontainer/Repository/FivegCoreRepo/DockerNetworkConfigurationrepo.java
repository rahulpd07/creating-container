package com.example.creatingcontainer.Repository.FivegCoreRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.creatingcontainer.Model.NetworkConfiguration;

import jakarta.transaction.Transactional;

public interface DockerNetworkConfigurationrepo extends JpaRepository<NetworkConfiguration, Long>{

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE NetworkConfiguration l SET  l.n2Ip=?1, l.n3Ip=?2, l.GatewayIp=?3 WHERE l.localControllerClientId=?4")
	public void updateIpWithNetworking(String n2Ip,String n3Ip,String gatewayIp, String localControllerClientId);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE NetworkConfiguration l SET  l.n2Ip=?1, l.n3Ip=?2 WHERE l.localControllerClientId=?3")
	public void updateIpwithoutNetworking(String n2Ip, String n3Ip, String localControllerClientId);

	@Query("SELECT n.GatewayIp FROM NetworkConfiguration n")
	public String upfdefaultGateway();
	
	@Query(value="SELECT n.n2ip FROM networkconfiguration n",nativeQuery=true)
	public String upfN2();
	
	@Query(value="SELECT n.n3ip FROM networkconfiguration n",nativeQuery=true)
	public String upfN3();

	@Query(value="SELECT n.docker_fivegcore_version FROM networkconfiguration n",nativeQuery=true)
	public String fetchVersionofFivegcore();
	
//	@Query(value="SELECT n.localControllerClientIdss FROM networkconfiguration n",nativeQuery=true)
//	public String fetchControllerId();

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE NetworkConfiguration l SET l.dockerfivegcoreversion=?1 WHERE l.localControllerClientId=?2")
	public void updatetheVersioninDatabase(String dockerFivegcoreVersion,String localControllerClientId);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE NetworkConfiguration l SET l.internetNetworking=?1 WHERE l.localControllerClientId=?2")
	public void updateNetworkingInDatabase(String networking, String localControllerClientId);

	@Query(value="SELECT n.internet_networking FROM networkconfiguration n",nativeQuery=true)
	public String fetchInternetNetworkingofFivegcore();






}
