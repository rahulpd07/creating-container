package com.example.creatingcontainer.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Bridge {
	 @JsonProperty("IPAMConfig") 
	    public Object iPAMConfig;
	    @JsonProperty("Links") 
	    public Object links;
	    @JsonProperty("Aliases") 
	    public Object aliases;
	    @JsonProperty("NetworkID") 
	    public String networkID;
	    @JsonProperty("EndpointID") 
	    public String endpointID;
	    @JsonProperty("Gateway") 
	    public String gateway;
	    @JsonProperty("IPAddress") 
	    public String iPAddress;
	    @JsonProperty("IPPrefixLen") 
	    public int iPPrefixLen;
	    @JsonProperty("IPv6Gateway") 
	    public String iPv6Gateway;
	    @JsonProperty("GlobalIPv6Address") 
	    public String globalIPv6Address;
	    @JsonProperty("GlobalIPv6PrefixLen") 
	    public int globalIPv6PrefixLen;
	    @JsonProperty("MacAddress") 
	    public String macAddress;
	    @JsonProperty("DriverOpts") 
	    public Object driverOpts;
		public Bridge() {
			super();
			// TODO Auto-generated constructor stub
		}
		public Bridge(Object iPAMConfig, Object links, Object aliases, String networkID, String endpointID,
				String gateway, String iPAddress, int iPPrefixLen, String iPv6Gateway, String globalIPv6Address,
				int globalIPv6PrefixLen, String macAddress, Object driverOpts) {
			super();
			this.iPAMConfig = iPAMConfig;
			this.links = links;
			this.aliases = aliases;
			this.networkID = networkID;
			this.endpointID = endpointID;
			this.gateway = gateway;
			this.iPAddress = iPAddress;
			this.iPPrefixLen = iPPrefixLen;
			this.iPv6Gateway = iPv6Gateway;
			this.globalIPv6Address = globalIPv6Address;
			this.globalIPv6PrefixLen = globalIPv6PrefixLen;
			this.macAddress = macAddress;
			this.driverOpts = driverOpts;
		}
		public Object getiPAMConfig() {
			return iPAMConfig;
		}
		public void setiPAMConfig(Object iPAMConfig) {
			this.iPAMConfig = iPAMConfig;
		}
		public Object getLinks() {
			return links;
		}
		public void setLinks(Object links) {
			this.links = links;
		}
		public Object getAliases() {
			return aliases;
		}
		public void setAliases(Object aliases) {
			this.aliases = aliases;
		}
		public String getNetworkID() {
			return networkID;
		}
		public void setNetworkID(String networkID) {
			this.networkID = networkID;
		}
		public String getEndpointID() {
			return endpointID;
		}
		public void setEndpointID(String endpointID) {
			this.endpointID = endpointID;
		}
		public String getGateway() {
			return gateway;
		}
		public void setGateway(String gateway) {
			this.gateway = gateway;
		}
		public String getiPAddress() {
			return iPAddress;
		}
		public void setiPAddress(String iPAddress) {
			this.iPAddress = iPAddress;
		}
		public int getiPPrefixLen() {
			return iPPrefixLen;
		}
		public void setiPPrefixLen(int iPPrefixLen) {
			this.iPPrefixLen = iPPrefixLen;
		}
		public String getiPv6Gateway() {
			return iPv6Gateway;
		}
		public void setiPv6Gateway(String iPv6Gateway) {
			this.iPv6Gateway = iPv6Gateway;
		}
		public String getGlobalIPv6Address() {
			return globalIPv6Address;
		}
		public void setGlobalIPv6Address(String globalIPv6Address) {
			this.globalIPv6Address = globalIPv6Address;
		}
		public int getGlobalIPv6PrefixLen() {
			return globalIPv6PrefixLen;
		}
		public void setGlobalIPv6PrefixLen(int globalIPv6PrefixLen) {
			this.globalIPv6PrefixLen = globalIPv6PrefixLen;
		}
		public String getMacAddress() {
			return macAddress;
		}
		public void setMacAddress(String macAddress) {
			this.macAddress = macAddress;
		}
		public Object getDriverOpts() {
			return driverOpts;
		}
		public void setDriverOpts(Object driverOpts) {
			this.driverOpts = driverOpts;
		}
	    
}
