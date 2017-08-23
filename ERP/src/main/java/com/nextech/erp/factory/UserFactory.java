package com.nextech.erp.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.model.User;
import com.nextech.erp.newDTO.UserDTO;



public class UserFactory {

	//TODO CALL TO ADD USER CONTROLLER
	public static User setUser(UserDTO userDTO,HttpServletRequest request) throws Exception{
		User user = new User();
		user.setId(userDTO.getId());
		user.setUserid(userDTO.getUserId());
		user.setPassword(userDTO.getPassword());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setMobile(userDTO.getMobileNo());
		user.setDob(userDTO.getDob());
		user.setDoj(userDTO.getDoj());
		user.setEmail(userDTO.getEmailId());
		user.setUsertype(userDTO.getUserType());
		user.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		user.setIsactive(true);
		user.setNotificationId(userDTO.getNotificationId());
		return user;
	}
	
	public static User setUserUpdate(UserDTO userDTO,HttpServletRequest request) throws Exception{
		User user = new User();
		user.setId(userDTO.getId());
		user.setUserid(userDTO.getUserId());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setMobile(userDTO.getMobileNo());
		user.setDob(userDTO.getDob());
		user.setDoj(userDTO.getDoj());
		user.setEmail(userDTO.getEmailId());
		user.setUsertype(userDTO.getUserType());
		user.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		user.setNotificationId(userDTO.getNotificationId());
		user.setIsactive(true);
		return user;
	}
	public static UserDTO setUserList(User user){
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setUserId(user.getUserid());
		userDTO.setPassword(user.getPassword());
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());
		userDTO.setMobileNo(user.getMobile());
		userDTO.setDob(user.getDob());
		userDTO.setDoj(user.getDoj());
		userDTO.setEmailId(user.getEmail());
		userDTO.setUserType(user.getUsertype());
		userDTO.setActive(true);
		userDTO.setCreatedBy(user.getCreatedBy());
		userDTO.setCreatedDate(user.getCreatedDate());
		userDTO.setUpdatedBy(user.getUpdatedBy());
		userDTO.setUpdatedDate(user.getUpdatedDate());
		userDTO.setNotificationId(user.getNotificationId());
		return userDTO;
	}

}
