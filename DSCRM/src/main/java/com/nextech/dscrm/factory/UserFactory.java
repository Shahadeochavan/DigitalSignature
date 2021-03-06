package com.nextech.dscrm.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.dscrm.model.User;
import com.nextech.dscrm.model.Usertype;
import com.nextech.dscrm.newDTO.UserDTO;
import com.nextech.dscrm.newDTO.UserTypeDTO;



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
		Usertype  usertype = new Usertype();
		usertype.setId(userDTO.getUserTypeDTO().getId());
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
		Usertype  usertype = new Usertype();
		usertype.setId(userDTO.getUserTypeDTO().getId());
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
		UserTypeDTO  userTypeDTO = new UserTypeDTO();
		userTypeDTO.setUsertypeName(user.getUsertype().getUsertypeName());
		userTypeDTO.setId(user.getUsertype().getId());
		userDTO.setUserTypeDTO(userTypeDTO);
		userDTO.setClientId(user.getClientId());
		userDTO.setActive(true);
		return userDTO;
	}

}
