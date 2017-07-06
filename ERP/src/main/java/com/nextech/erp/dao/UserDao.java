package com.nextech.erp.dao;

import java.util.List;

import com.nextech.erp.model.User;

public interface UserDao extends SuperDao<User>{
	public User getUserByUserId(String userId) throws Exception;

	public User getUserByEmail(String email) throws Exception;

	public User getUserByMobile(String mobile) throws Exception;
	
	public List<User> getUserProfileByUserId(long id) throws Exception;
	
	public User getUserByFirstNamLastName(String firstName,String lastName) throws Exception;
	
	public User getEmailUserById(long id) throws Exception;
	
	public User getUserByContact(String contact) throws Exception;
}
