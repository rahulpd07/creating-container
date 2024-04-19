package com.example.creatingcontainer.Dto;

public class ImageVersionDto {

	private String mongoVersion;
	private String mySqlVersion;
	private String backendVersion;
	private String frontendVersion;
	private String fiveGversion;
	public String getMongoVersion() {
		return mongoVersion;
	}
	public void setMongoVersion(String mongoVersion) {
		this.mongoVersion = mongoVersion;
	}
	public String getMySqlVersion() {
		return mySqlVersion;
	}
	public void setMySqlVersion(String mySqlVersion) {
		this.mySqlVersion = mySqlVersion;
	}
	public String getBackendVersion() {
		return backendVersion;
	}
	public void setBackendVersion(String backendVersion) {
		this.backendVersion = backendVersion;
	}
	public String getFrontendVersion() {
		return frontendVersion;
	}
	public void setFrontendVersion(String frontendVersion) {
		this.frontendVersion = frontendVersion;
	}
	public String getFiveGversion() {
		return fiveGversion;
	}
	public void setFiveGversion(String fiveGversion) {
		this.fiveGversion = fiveGversion;
	}
	
}
