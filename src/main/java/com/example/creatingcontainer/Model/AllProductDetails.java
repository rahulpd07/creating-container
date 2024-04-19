package com.example.creatingcontainer.Model;

import java.time.LocalDateTime;
 

public class AllProductDetails {

	public String productName;
    
	public String productVersion;
    
	public boolean product_scheduled_update;
    
	public LocalDateTime product_scheduled_update_dateTime;
    
	public String task;

	public AllProductDetails(String productName, String productVersion, boolean product_scheduled_update,
			LocalDateTime product_scheduled_update_dateTime, String task) {
		super();
		this.productName = productName;
		this.productVersion = productVersion;
		this.product_scheduled_update = product_scheduled_update;
		this.product_scheduled_update_dateTime = product_scheduled_update_dateTime;
		this.task = task;
	}

	public AllProductDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductVersion() {
		return productVersion;
	}

	public void setProductVersion(String productVersion) {
		this.productVersion = productVersion;
	}

	public boolean isProduct_scheduled_update() {
		return product_scheduled_update;
	}

	public void setProduct_scheduled_update(boolean product_scheduled_update) {
		this.product_scheduled_update = product_scheduled_update;
	}

	public LocalDateTime getProduct_scheduled_update_dateTime() {
		return product_scheduled_update_dateTime;
	}

	public void setProduct_scheduled_update_dateTime(LocalDateTime product_scheduled_update_dateTime) {
		this.product_scheduled_update_dateTime = product_scheduled_update_dateTime;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}
    
    
	 
    

	
    
    
	 
}
