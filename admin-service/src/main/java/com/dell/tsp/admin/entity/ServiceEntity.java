package com.dell.tsp.admin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "service")
public class ServiceEntity {

	
	private int serviceId;
	private String serviceName;
	private String serviceDesc;
	
	public ServiceEntity() {
		super();
	}
	public ServiceEntity(int serviceId, String serviceName, String serviceDesc) {
		super();
		this.serviceId = serviceId;
		this.serviceName = serviceName;
		this.serviceDesc = serviceDesc;
	}
	
	@Id
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	
	@Column(name="SERVICE_NAME", nullable = false, unique = true)
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	@Column(name="SERVICE_DESC", nullable = false)
	public String getServiceDesc() {
		return serviceDesc;
	}
	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}
}
