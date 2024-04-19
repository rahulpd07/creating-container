package com.example.creatingcontainer.Dto;

public class ProductRootDto {

	  public boolean provisionStatus;
	    public SiteDetails siteDetails;
	    public Update update;
		public ProductRootDto() {
			super();
			// TODO Auto-generated constructor stub
		}
		public ProductRootDto(boolean provisionStatus, SiteDetails siteDetails, Update update) {
			super();
			this.provisionStatus = provisionStatus;
			this.siteDetails = siteDetails;
			this.update = update;
		}
		public boolean isProvisionStatus() {
			return provisionStatus;
		}
		public void setProvisionStatus(boolean provisionStatus) {
			this.provisionStatus = provisionStatus;
		}
		public SiteDetails getSiteDetails() {
			return siteDetails;
		}
		public void setSiteDetails(SiteDetails siteDetails) {
			this.siteDetails = siteDetails;
		}
		public Update getUpdate() {
			return update;
		}
		public void setUpdate(Update update) {
			this.update = update;
		}
	    
	
}
