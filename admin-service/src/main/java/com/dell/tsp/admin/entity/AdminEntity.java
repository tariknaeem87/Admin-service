package com.dell.tsp.admin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "admin")
public class AdminEntity {

	
	private int id;
            private String userName;
            private String passWord;
            private String adminFirstName;
            private String adminLastName;
            private String adminEmail;
            private long mobileNo;
                       
            public AdminEntity() {
				super();
			}

			public AdminEntity(int id, String userName, String passWord, String adminFirstName, String adminLastName,
					String adminEmail, long mobileNo) {
				super();
				this.id = id;
				this.userName = userName;
				this.passWord = passWord;
				this.adminFirstName = adminFirstName;
				this.adminLastName = adminLastName;
				this.adminEmail = adminEmail;
				this.mobileNo = mobileNo;
			}

			@Id
			@GeneratedValue()
			public int getId() {
				return id;
			}

			public void setId(int id) {
				this.id = id;
			}

			@Column(name="USER_NAME", nullable = false,unique = true)
			public String getUserName() {
				return userName;
			}
			
			public void setUserName(String userName) {
				this.userName = userName;
			}
			
			@Column(name="PASSWORD", nullable = false)
			public String getPassWord() {
				return passWord;
			}
			
			public void setPassWord(String passWord) {
				this.passWord = passWord;
			}

			@Column(name="first_name", nullable = false)
			public String getAdminFirstName() {
				return adminFirstName;
			}

			public void setAdminFirstName(String adminFirstName) {
				this.adminFirstName = adminFirstName;
			}

			@Column(name="last_name", nullable = false)
			public String getAdminLastName() {
				return adminLastName;
			}

			public void setAdminLastName(String adminLastName) {
				this.adminLastName = adminLastName;
			}

			@Column(name="email", nullable = false, unique = true)
			public String getAdminEmail() {
				return adminEmail;
			}

			public void setAdminEmail(String adminEmail) {
				this.adminEmail = adminEmail;
			}

			@Column(name="mobile_no", nullable = false, unique = true)
			public long getMobileNo() {
				return mobileNo;
			}

			public void setMobileNo(long mobileNo) {
				this.mobileNo = mobileNo;
			}          
}
