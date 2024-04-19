package com.example.creatingcontainer.Model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Dto {

	 @JsonProperty("Id") 
	    public String id;
	    @JsonProperty("Names") 
	    public ArrayList<String> names;
	    @JsonProperty("Image") 
	    public String image;
	    @JsonProperty("ImageID") 
	    public String imageID;
	    @JsonProperty("Command") 
	    public String command;
	    @JsonProperty("Created") 
	    public int created;
	    @JsonProperty("Ports") 
	    public ArrayList<Port> ports;
	    @JsonProperty("Labels") 
	    public Labels labels;
	    @JsonProperty("State") 
	    public String state;
	    @JsonProperty("Status") 
	    public String status;
	    @JsonProperty("HostConfig") 
	    public HostConfig hostConfig;
	    @JsonProperty("NetworkSettings") 
	    public NetworkSettings networkSettings;
	    @JsonProperty("Mounts") 
	    public ArrayList<Mount> mounts;
		public Dto() {
			super();
			// TODO Auto-generated constructor stub
		}
		public Dto(String id, ArrayList<String> names, String image, String imageID, String command, int created,
				ArrayList<Port> ports, Labels labels, String state, String status, HostConfig hostConfig,
				NetworkSettings networkSettings, ArrayList<Mount> mounts) {
			super();
			this.id = id;
			this.names = names;
			this.image = image;
			this.imageID = imageID;
			this.command = command;
			this.created = created;
			this.ports = ports;
			this.labels = labels;
			this.state = state;
			this.status = status;
			this.hostConfig = hostConfig;
			this.networkSettings = networkSettings;
			this.mounts = mounts;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public ArrayList<String> getNames() {
			return names;
		}
		public void setNames(ArrayList<String> names) {
			this.names = names;
		}
		public String getImage() {
			return image;
		}
		public void setImage(String image) {
			this.image = image;
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
		public int getCreated() {
			return created;
		}
		public void setCreated(int created) {
			this.created = created;
		}
		public ArrayList<Port> getPorts() {
			return ports;
		}
		public void setPorts(ArrayList<Port> ports) {
			this.ports = ports;
		}
		public Labels getLabels() {
			return labels;
		}
		public void setLabels(Labels labels) {
			this.labels = labels;
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
		public HostConfig getHostConfig() {
			return hostConfig;
		}
		public void setHostConfig(HostConfig hostConfig) {
			this.hostConfig = hostConfig;
		}
		public NetworkSettings getNetworkSettings() {
			return networkSettings;
		}
		public void setNetworkSettings(NetworkSettings networkSettings) {
			this.networkSettings = networkSettings;
		}
		public ArrayList<Mount> getMounts() {
			return mounts;
		}
		public void setMounts(ArrayList<Mount> mounts) {
			this.mounts = mounts;
		}
	    
}
