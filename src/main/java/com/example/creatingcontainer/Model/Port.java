package com.example.creatingcontainer.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Port {

	 @JsonProperty("IP") 
	    public String iP;
	    @JsonProperty("PrivatePort") 
	    public int privatePort;
	    @JsonProperty("PublicPort") 
	    public int publicPort;
	    @JsonProperty("Type") 
	    public String type;
		public Port() {
			super();
			// TODO Auto-generated constructor stub
		}
		public Port(String iP, int privatePort, int publicPort, String type) {
			super();
			this.iP = iP;
			this.privatePort = privatePort;
			this.publicPort = publicPort;
			this.type = type;
		}
		public String getiP() {
			return iP;
		}
		public void setiP(String iP) {
			this.iP = iP;
		}
		public int getPrivatePort() {
			return privatePort;
		}
		public void setPrivatePort(int privatePort) {
			this.privatePort = privatePort;
		}
		public int getPublicPort() {
			return publicPort;
		}
		public void setPublicPort(int publicPort) {
			this.publicPort = publicPort;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
	    
	    
}
