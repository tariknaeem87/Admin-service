package com.dell.tsp.admin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dell.tsp.admin.DTO.AdminDTO;
import com.dell.tsp.admin.entity.AdminEntity;
import com.dell.tsp.admin.repository.AdminRepository;

@Service
public class LoginService {
	private static final Logger log = LoggerFactory.getLogger(LoginService.class);
	
	
	@Autowired
	private AdminRepository adminRepository; 
	
	public String getLoginDetails(String userName, String password) {
		log.info("Inside login method");
		AdminEntity adminEntity = adminRepository.findByUserName(userName);
		String passWord= adminEntity.getPassWord();
		
		//String passWord = dataDao.findByUserName(userName);
		String status = "";
		if(passWord.equals(password)) {
			status = "Successful Login!";
		}
		
		if(passWord.equals("User not found")) {
			status = "User Not Found!";
		}
		
		 if(!passWord.equals("User not found") && !passWord.equals(password)){
			status = "Invalid Password! Please try again";
		}
		return status;
	}
	
	public AdminEntity createAdmin(AdminDTO admin) {
		log.info("Inside createAdmin method");
		AdminEntity adminEntity = new AdminEntity();
		adminEntity.setAdminEmail(admin.getAdminEmail());
		adminEntity.setAdminFirstName(admin.getAdminFirstName());
		adminEntity.setAdminLastName(admin.getAdminLastName());
		adminEntity.setMobileNo(admin.getMobileNo());
		adminEntity.setPassWord(admin.getPassWord());
		adminEntity.setUserName(admin.getUserName());
		return adminRepository.save(adminEntity);
	} 
 


}
