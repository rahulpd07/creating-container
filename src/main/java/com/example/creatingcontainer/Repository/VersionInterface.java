//package com.example.creatingcontainer.Repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//
//import com.example.creatingcontainer.Model.ImagesVersion;
//
//import jakarta.transaction.Transactional;
// 
//
//public interface VersionInterface extends JpaRepository<ImagesVersion, Long>{
//
//	
// 
////	@Transactional
////	@Modifying
////	@Query("UPDATE ImagesVersion i SET i.keycloakVersion= '23.0.3' where i.id = 1L")
////	public void updateV(String keycloakVersion, long id);
// 
//	
//@Query("SELECT COUNT(l) FROM ImagesVersion l")
//public int countImageVersion();
//// 
//@Modifying
//@Transactional
//@Query("UPDATE ImagesVersion i SET i.mongoVersion = ?1, i.mySqlVersion= ?2, i.backendVersion= ?3, i.frontendVersion = ?4 where i.uniqueNumber = ?5")
//public void updateImageVersion(String mongoVersion, String mySqlVersion, String backendVersion, String frontendVersion,String uniqueNumber);
//}
