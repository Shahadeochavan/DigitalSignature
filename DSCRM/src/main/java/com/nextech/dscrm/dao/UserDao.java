package com.nextech.dscrm.dao;

import java.util.List;

import com.nextech.dscrm.model.User;

public interface UserDao extends SuperDao<User>{
	
	public User getUserByUserId(String userId) throws Exception;

	public User getUserByEmail(String email) throws Exception;

	public User getUserByMobile(String mobile) throws Exception;
	
	public User getUserByNotifictionId(long notificatinId) throws Exception;
	
	public List<User> getMultipleUsersById(List<Long> ids) throws Exception;
}
