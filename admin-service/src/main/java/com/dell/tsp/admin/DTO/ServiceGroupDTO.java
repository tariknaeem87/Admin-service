package com.dell.tsp.admin.DTO;

public class ServiceGroupDTO {
	private int serviceGroupId;
    private String services;
	public int getServiceGroupId() {
		return serviceGroupId;
	}
	public void setServiceGroupId(int serviceGroupId) {
		this.serviceGroupId = serviceGroupId;
	}
	public String getServices() {
		return services;
	}
	public void setServices(String services) {
		this.services = services;
	}
}
