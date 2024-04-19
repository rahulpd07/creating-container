package com.example.creatingcontainer.Model;

import java.util.List;

public class DockerContainerInfo {

	 private String Id;
	    private List<String> Names;
	    private String Image;
		public String getId() {
			return Id;
		}
		public void setId(String id) {
			Id = id;
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
		
	    

}
