package com.nextech.erp.service;

import java.util.List;

import com.nextech.erp.model.User;
import com.nextech.erp.newDTO.UserDTO;

public interface UserService extends CRUDService<User>{

	public User findByUserId(String string) throws Exception;

	public User getUserByUserId(String userId) throws Exception;

	public User getUserByEmail(String email) throws Exception;

	public User getUserByMobile(String mobile) throws Exception;
	
	public List<User> getUserProfileByUserId(long id) throws Exception;
	
	public User getUserByFirstNamLastName(String firstName,String lastName) throws Exception;
	
	public User getUserByNotifictionId(long notificatinId) throws Exception;
	
	public User getEmailUserById(long id) throws Exception;
	
	public User getUserByContact(String contact) throws Exception;
	
	public List<UserDTO> getUserList(List<UserDTO> userDTOs)throws Exception;
	
	public UserDTO getUserDTO(long id) throws Exception;
	
	public void getUserDTOByid(long id)throws Exception;
	
	
	
}
