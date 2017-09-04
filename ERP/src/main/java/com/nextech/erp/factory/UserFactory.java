package com.nextech.erp.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.model.User;
import com.nextech.erp.model.Usertype;
import com.nextech.erp.newDTO.UserDTO;
import com.nextech.erp.newDTO.UserTypeDTO;



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
		Usertype  usertype =  new Usertype();
		usertype.setId(userDTO.getUserType());
		user.setUsertype(usertype);
		user.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		user.setIsactive(true);
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
		Usertype  usertype =  new Usertype();
		usertype.setId(userDTO.getUserType());
		user.setUsertype(usertype);
		user.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
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
		UserTypeDTO userTypeDTO =  new UserTypeDTO();
		userTypeDTO.setId(user.getUsertype().getId());
		userTypeDTO.setUsertypeName(user.getUsertype().getUsertypeName());
		userDTO.setUserTypeDTO(userTypeDTO);
		userDTO.setActive(true);
		userDTO.setCreatedBy(user.getCreatedBy());
		userDTO.setCreatedDate(user.getCreatedDate());
		userDTO.setUpdatedBy(user.getUpdatedBy());
		userDTO.setUpdatedDate(user.getUpdatedDate());
		return userDTO;
	}

}
