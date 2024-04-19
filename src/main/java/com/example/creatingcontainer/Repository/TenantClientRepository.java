//package com.example.creatingcontainer.Repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import com.example.creatingcontainer.Model.AllProductDetails;
//
// 
// 
//
//public interface TenantClientRepository extends JpaRepository<AllProductDetails ,String>{
//
//	@Query("SELECT COUNT(l) FROM AllProductDetails l")
//	public int countProducts();
//}
