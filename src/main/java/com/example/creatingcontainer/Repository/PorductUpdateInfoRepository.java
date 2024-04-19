package com.example.creatingcontainer.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.creatingcontainer.Model.PorductUpdateInfo;

import jakarta.transaction.Transactional;

@EnableJpaRepositories
public interface PorductUpdateInfoRepository extends JpaRepository<PorductUpdateInfo , Long>{

	@Query("SELECT COUNT(l) FROM PorductUpdateInfo l")
	public int countProductsUpdateInfo();
	
	 public PorductUpdateInfo findByProductName(String productName);
	  
	 	@Modifying(clearAutomatically = true)
		@Transactional
		@Query("UPDATE PorductUpdateInfo i SET i.tenantId=?1 WHERE i.deploymentId=?2")
		public void updateProductInfoTenentUpdate(String tenantId,String deploymentId);
	  
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE PorductUpdateInfo i SET i.productVersion=?4, i.product_scheduled_update =?5, i.product_scheduled_update_dateTime=?6, i.updateAvailable=?7, i.task=?8  WHERE i.deploymentId=?1 AND i.tenantId=?2 AND i.productName=?3")
	public void updateProductInfo(String deploymentId,String tenantId, String productName,String productVersion,
			boolean product_scheduled_update,LocalDateTime product_scheduled_update_dateTime, boolean updateAvailable,String task);

	
@Modifying(clearAutomatically = true)
@Transactional	
@Query("UPDATE PorductUpdateInfo i SET i.productVersion=?4, i.product_scheduled_update =?5, i.product_scheduled_update_dateTime=?6, i.task=?7 WHERE i.deploymentId=?1 AND i.tenantId=?2 AND i.productName=?3")
public void updateDateTimeScheduled(String deploymentId,String tenantId, String productName,String productVersion,
		boolean product_scheduled_update,LocalDateTime product_scheduled_update_dateTime,String task);
	 
 
//@Query(value = "SELECT product_Version FROM Porduct_Update_Info WHERE product_Name = ?1 " +
//        "UNION " +
//        "SELECT product_Version FROM Porduct_Update_Info WHERE product_Name = ?2", nativeQuery = true)
//public ArrayList<String> findVersionsByProductName(String productName1, String productName2);

@Query(value = "SELECT product_Name, product_Version FROM Porduct_Update_Info WHERE product_Name = ?1 " +
        "UNION " +
        "SELECT product_Name, product_Version FROM Porduct_Update_Info WHERE product_Name = ?2", nativeQuery = true)
List<Object[]> findProductNamesAndVersions(String productName1, String productName2);


	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE PorductUpdateInfo i SET i.updateAvailable=?1 WHERE i.productName=?2")
	public void updateTheAvailableUpdateInTable(boolean updateAvailable,String productName);
}




