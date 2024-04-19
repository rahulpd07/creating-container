package com.example.creatingcontainer.Model;

import java.util.ArrayList;
import java.util.List;

public class ContainerId {

	private String Id;
    private List<Object> warnings = new ArrayList<Object>();
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
