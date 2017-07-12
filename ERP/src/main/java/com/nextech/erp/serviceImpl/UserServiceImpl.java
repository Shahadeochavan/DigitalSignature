package com.nextech.erp.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.UserDao;
import com.nextech.erp.model.User;
import com.nextech.erp.service.UserService;
@Service
@Qualifier("userServiceImpl")
public class UserServiceImpl extends CRUDServiceImpl<User> implements UserService {
	
	@Autowired
	UserDao userdao;

	@Override
	public User findByUserId(String string) throws Exception {
		return null;
	}

	@Override
	public User getUserByUserId(String userid) throws Exception {
		return userdao.getUserByUserId(userid);
	}

	@Override
	public User getUserByEmail(String email) throws Exception {
		return userdao.getUserByEmail(email);
	}

	@Override
	public User getUserByMobile(String mobile) throws Exception {
		return userdao.getUserByMobile(mobile);
	}

	@Override
	public List<User> getUserProfileByUserId(long id) throws Exception {
		// TODO Auto-generated method stub
		return userdao.getUserProfileByUserId(id);
	}

	@Override
	public User getUserByFirstNamLastName(String firstName,String lastName) throws Exception {
		// TODO Auto-generated method stub
		return userdao.getUserByFirstNamLastName(firstName, lastName);
	}

	@Override
	public User getUserByNotifictionId(long notificatinId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getEmailUserById(long id) throws Exception {
		// TODO Auto-generated method stub
		return userdao.getEmailUserById(id);
	}

	@Override
	public User getUserByContact(String contact) throws Exception {
		// TODO Auto-generated method stub
		return userdao.getUserByContact(contact);
	}
	
}
