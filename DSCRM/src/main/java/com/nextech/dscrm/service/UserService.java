package com.nextech.dscrm.service;

import java.util.List;

import com.nextech.dscrm.model.User;
import com.nextech.dscrm.newDTO.UserDTO;

public interface UserService extends CRUDService<User>{

	public User getUserByUserId(String userId) throws Exception;

	public User getUserByEmail(String email) throws Exception;

	public User getUserByMobile(String mobile) throws Exception;
	
	public List<UserDTO> getUserList(List<UserDTO> userDTOs)throws Exception;
	
	public UserDTO getUserDTO(long id) throws Exception;
	
	public UserDTO deleteUser(long id)throws Exception;
	
}
