package com.dell.tsp.admin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "service_group")
public class ServiceGroupEntity {
	
	public ServiceGroupEntity() {
		super();
	}
	
	public ServiceGroupEntity(int serviceGroupId, String services) {
		super();
		this.serviceGroupId = serviceGroupId;
		this.services = services;
	}
	
	private int serviceGroupId;
    private String services;

	
    @Id
    @Column(name="SERVICE_GROUP_ID", nullable = false)		
    public int getServiceGroupId() {
		return serviceGroupId;
	}
	public void setServiceGroupId(int serviceGroupId) {
		this.serviceGroupId = serviceGroupId;
	}
	
    @Column(name="SERVICES", nullable = false)
	public String getServices() {
		return services;
	}
	public void setServices(String services) {
		this.services = services;
	}
		
         

}
