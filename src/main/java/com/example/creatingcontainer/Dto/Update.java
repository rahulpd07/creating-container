package com.example.creatingcontainer.Dto;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Update {

	    public boolean updateAvailable;
	    public ArrayList<Product> products;
	    public boolean scheduledUpdate;
	    public LocalDateTime updateDateTime;
		public Update() {
			super();
			// TODO Auto-generated constructor stub
		}
		public Update(boolean updateAvailable, ArrayList<Product> products, boolean scheduledUpdate,
				LocalDateTime updateDateTime) {
			super();
			this.updateAvailable = updateAvailable;
			this.products = products;
			this.scheduledUpdate = scheduledUpdate;
			this.updateDateTime = updateDateTime;
		}
		public boolean isUpdateAvailable() {
			return updateAvailable;
		}
		public void setUpdateAvailable(boolean updateAvailable) {
			this.updateAvailable = updateAvailable;
		}
		public ArrayList<Product> getProducts() {
			return products;
		}
		public void setProducts(ArrayList<Product> products) {
			this.products = products;
		}
		public boolean isScheduledUpdate() {
			return scheduledUpdate;
		}
		public void setScheduledUpdate(boolean scheduledUpdate) {
			this.scheduledUpdate = scheduledUpdate;
		}
		public LocalDateTime getUpdateDateTime() {
			return updateDateTime;
		}
		public void setUpdateDateTime(LocalDateTime updateDateTime) {
			this.updateDateTime = updateDateTime;
		}
	    
	    
	
}
