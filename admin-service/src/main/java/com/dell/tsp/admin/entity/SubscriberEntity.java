package com.dell.tsp.admin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "subscribers")
public class SubscriberEntity {
	
    private long subscriberId;
    private String firstName;
    private String lastName;
    private String adharNo;
    private String address;
    private String email;
    private long mobileNo;
    
	public SubscriberEntity() {
		super();
	}
	public SubscriberEntity(long subscriberId, String firstName, String adharNo, String address,
			String lastName, String email, long mobileNo) {
		super();
		this.subscriberId = subscriberId;
		this.firstName = firstName;
		this.adharNo = adharNo;
		this.address = address;
		this.lastName = lastName;
		this.email = email;
		this.mobileNo = mobileNo;
	}
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	public long getSubscriberId() {
		return subscriberId;
	}
	public void setSubscriberId(long subscriberId) {
		this.subscriberId = subscriberId;
	}
	@Column(name = "first_name", nullable = false)
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	@Column(name = "aadhar_no", nullable = false)
	public String getAdharNo() {
		return adharNo;
	}
	public void setAdharNo(String adharNo) {
		this.adharNo = adharNo;
	}
	@Column(name = "address", nullable = false)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(name = "last_name", nullable = false)
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@Column(name = "email_address", nullable = false)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name = "mobile_no", nullable = false,unique = true)
	public long getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}
	@Override
	public String toString() {
		return "SubscriberEntity [subscriberId=" + subscriberId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", adharNo=" + adharNo + ", address=" + address + ", email=" + email + ", mobileNo=" + mobileNo + "]";
	}
}
