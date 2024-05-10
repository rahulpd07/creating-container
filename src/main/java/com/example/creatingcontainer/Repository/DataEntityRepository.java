package com.example.creatingcontainer.Repository;

import com.example.creatingcontainer.Model.DataEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DataEntityRepository extends JpaRepository<DataEntity, Long> {
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE DataEntity i SET i.provisionStatus=?1, i.siteName=?2, i.streetName=?3, i.city=?4, i.pinCode=?5, i.state=?6, i.country=?7, i.fullName=?8, i.contact=?9, i.email=?10 WHERE i.deploymentId=?11")
    public void updateDataEntityTable(boolean provisionStatus,String siteName,String streetName,String city,String pinCode,String state,String country, String fullName,String contact,String email,String deploymentId);
}
