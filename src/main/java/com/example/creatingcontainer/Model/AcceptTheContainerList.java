package com.example.creatingcontainer.Model;

import java.util.List;
import java.util.Map;

public class AcceptTheContainerList {

	 private String id;
	    private List<String> Names;
	    private String Image;
	    private String imageID;
	    private String command;
	    private long created;
	    private String state;
	    private String status;
	    private List<Map<String, Object>> ports;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public List<String> getNames() {
			return Names;
		}
		public void setNames(List<String> names) {
			Names = names;
		}
		public String getImage() {
			return Image;
		}
		public void setImage(String image) {
			Image = image;
		}
		public String getImageID() {
			return imageID;
		}
		public void setImageID(String imageID) {
			this.imageID = imageID;
		}
		public String getCommand() {
			return command;
		}
		public void setCommand(String command) {
			this.command = command;
		}
		public long getCreated() {
			return created;
		}
		public void setCreated(long created) {
			this.created = created;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public List<Map<String, Object>> getPorts() {
			return ports;
		}
		public void setPorts(List<Map<String, Object>> ports) {
			this.ports = ports;
		}
		
	    

}
