//package com.example.creatingcontainer.Model;
//
// 
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//
//@Entity
//@Table(name = "Image_version")
//public class ImagesVersion {
//	 
//	@Id
//	@Column(name = "id")
//	@GeneratedValue(strategy = GenerationType.SEQUENCE)
//	  long id;
//	@Column(name = "mongo_Version")
//	private String mongoVersion;
//	@Column(name = "mySql_Version")
//	private String mySqlVersion;
//	@Column(name = "backend_Version")
//	private String backendVersion;
//	@Column(name = "frontend_Version")
//	private String frontendVersion;
//	@Column(name = "unique_number")
//	private String uniqueNumber;
//	@Column(name = "five_G_version")
//	private String fiveGversion;
//	public ImagesVersion() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//	public ImagesVersion(long id, String mongoVersion, String mySqlVersion, String backendVersion,
//			String frontendVersion, String uniqueNumber, String fiveGversion) {
//		super();
//		this.id = id;
//		this.mongoVersion = mongoVersion;
//		this.mySqlVersion = mySqlVersion;
//		this.backendVersion = backendVersion;
//		this.frontendVersion = frontendVersion;
//		this.uniqueNumber = uniqueNumber;
//		this.fiveGversion = fiveGversion;
//	}
//	public long getId() {
//		return id;
//	}
//	public void setId(long id) {
//		this.id = id;
//	}
//	public String getMongoVersion() {
//		return mongoVersion;
//	}
//	public void setMongoVersion(String mongoVersion) {
//		this.mongoVersion = mongoVersion;
//	}
//	public String getMySqlVersion() {
//		return mySqlVersion;
//	}
//	public void setMySqlVersion(String mySqlVersion) {
//		this.mySqlVersion = mySqlVersion;
//	}
//	public String getBackendVersion() {
//		return backendVersion;
//	}
//	public void setBackendVersion(String backendVersion) {
//		this.backendVersion = backendVersion;
//	}
//	public String getFrontendVersion() {
//		return frontendVersion;
//	}
//	public void setFrontendVersion(String frontendVersion) {
//		this.frontendVersion = frontendVersion;
//	}
//	public String getUniqueNumber() {
//		return uniqueNumber;
//	}
//	public void setUniqueNumber(String uniqueNumber) {
//		this.uniqueNumber = uniqueNumber;
//	}
//	public String getFiveGversion() {
//		return fiveGversion;
//	}
//	public void setFiveGversion(String fiveGversion) {
//		this.fiveGversion = fiveGversion;
//	}
//	 
//	
//	
//}
