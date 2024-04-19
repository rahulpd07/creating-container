package com.example.creatingcontainer.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Mount {
	 @JsonProperty("Type") 
	    public String type;
	    @JsonProperty("Name") 
	    public String name;
	    @JsonProperty("Source") 
	    public String source;
	    @JsonProperty("Destination") 
	    public String destination;
	    @JsonProperty("Driver") 
	    public String driver;
	    @JsonProperty("Mode") 
	    public String mode;
	    @JsonProperty("RW") 
	    public boolean rW;
	    @JsonProperty("Propagation") 
	    public String propagation;
		public Mount() {
			super();
			// TODO Auto-generated constructor stub
		}
		public Mount(String type, String name, String source, String destination, String driver, String mode,
				boolean rW, String propagation) {
			super();
			this.type = type;
			this.name = name;
			this.source = source;
			this.destination = destination;
			this.driver = driver;
			this.mode = mode;
			this.rW = rW;
			this.propagation = propagation;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getSource() {
			return source;
		}
		public void setSource(String source) {
			this.source = source;
		}
		public String getDestination() {
			return destination;
		}
		public void setDestination(String destination) {
			this.destination = destination;
		}
		public String getDriver() {
			return driver;
		}
		public void setDriver(String driver) {
			this.driver = driver;
		}
		public String getMode() {
			return mode;
		}
		public void setMode(String mode) {
			this.mode = mode;
		}
		public boolean isrW() {
			return rW;
		}
		public void setrW(boolean rW) {
			this.rW = rW;
		}
		public String getPropagation() {
			return propagation;
		}
		public void setPropagation(String propagation) {
			this.propagation = propagation;
		}
	    
}
