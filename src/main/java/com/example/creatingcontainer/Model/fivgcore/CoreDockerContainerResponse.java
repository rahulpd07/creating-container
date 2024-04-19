package com.example.creatingcontainer.Model.fivgcore;

import java.util.ArrayList;
import java.util.List;

public class CoreDockerContainerResponse {

	public String Id;
	private List<Object> warnings = new ArrayList<Object>();
	public CoreDockerContainerResponse(String id, List<Object> warnings) {
		super();
		Id = id;
		this.warnings = warnings;
	}
	public CoreDockerContainerResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public List<Object> getWarnings() {
		return warnings;
	}
	public void setWarnings(List<Object> warnings) {
		this.warnings = warnings;
	}
	
	
	

}
