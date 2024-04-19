package com.example.creatingcontainer.Model;

public class DockerContainerNames {

	public String mongo;
	public String mysql;
    public String sdn5G;
	public String frontend;
	public DockerContainerNames() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DockerContainerNames(String mongo, String mysql, String sdn5g, String frontend) {
		super();
		this.mongo = mongo;
		this.mysql = mysql;
		sdn5G = sdn5g;
		this.frontend = frontend;
	}
	public String getMongo() {
		return mongo;
	}
	public void setMongo(String mongo) {
		this.mongo = mongo;
	}
	public String getMysql() {
		return mysql;
	}
	public void setMysql(String mysql) {
		this.mysql = mysql;
	}
	public String getSdn5G() {
		return sdn5G;
	}
	public void setSdn5G(String sdn5g) {
		sdn5G = sdn5g;
	}
	public String getFrontend() {
		return frontend;
	}
	public void setFrontend(String frontend) {
		this.frontend = frontend;
	}
	
	
}
