package com.example.creatingcontainer.Model;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "Porduct_Update_Info")
@Entity
public class PorductUpdateInfo {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	@Column(name = "deployment_Id")
	private String deploymentId;
	@Column(name = "tenant_Id")
	private String tenantId;
	@Column(name = "product_Name")
	private String productName;
	@Column(name = "product_Version")
	private String productVersion;
	@Column(name = "product_scheduled_update")
	private boolean product_scheduled_update;
	@Column(name = "product_scheduled_update_dateTime")
	private LocalDateTime  product_scheduled_update_dateTime;
	@Column(name = "task")
	private String task;
	@Column(name ="update_available")
	private boolean updateAvailable;
	public PorductUpdateInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PorductUpdateInfo(long id, String deploymentId, String tenantId, String productName, String productVersion,
			boolean product_scheduled_update, LocalDateTime product_scheduled_update_dateTime, String task,
			boolean updateAvailable) {
		super();
		this.id = id;
		this.deploymentId = deploymentId;
		this.tenantId = tenantId;
		this.productName = productName;
		this.productVersion = productVersion;
		this.product_scheduled_update = product_scheduled_update;
		this.product_scheduled_update_dateTime = product_scheduled_update_dateTime;
		this.task = task;
		this.updateAvailable = updateAvailable;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDeploymentId() {
		return deploymentId;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
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
	public boolean isUpdateAvailable() {
		return updateAvailable;
	}
	public void setUpdateAvailable(boolean updateAvailable) {
		this.updateAvailable = updateAvailable;
	}
	 
	 
}
