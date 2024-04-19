package com.example.creatingcontainer.Model;

public class DockerImageNames {
	 String backendName;
     String mysqlName;
	 String mongoName;
	 String frontName;
	public DockerImageNames() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DockerImageNames(String backendName, String mysqlName, String mongoName, String frontName) {
		super();
		this.backendName = backendName;
		this.mysqlName = mysqlName;
		this.mongoName = mongoName;
		this.frontName = frontName;
	}
	public String getBackendName() {
		return backendName;
	}
	public void setBackendName(String backendName) {
		this.backendName = backendName;
	}
	public String getMysqlName() {
		return mysqlName;
	}
	public void setMysqlName(String mysqlName) {
		this.mysqlName = mysqlName;
	}
	public String getMongoName() {
		return mongoName;
	}
	public void setMongoName(String mongoName) {
		this.mongoName = mongoName;
	}
	public String getFrontName() {
		return frontName;
	}
	public void setFrontName(String frontName) {
		this.frontName = frontName;
	}
	 
	
}
