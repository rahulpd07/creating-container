package com.example.creatingcontainer.Model;

import java.util.List;

public class startResponseBodyData {

	private String id;
    private List<String> names;
    private String image;
    private String imageID;
    private String command;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public List<String> getNames() {
			return names;
		}
		public void setNames(List<String> names) {
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

}
