package com.nextech.erp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.UserDao;
import com.nextech.erp.factory.PageFactory;
import com.nextech.erp.factory.UserFactory;
import com.nextech.erp.model.Page;
import com.nextech.erp.model.User;
import com.nextech.erp.newDTO.PageDTO;
import com.nextech.erp.newDTO.UserDTO;
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

	@Override
	public List<UserDTO> getUserList(List<UserDTO> userDTOs)throws Exception {
		// TODO Auto-generated method stub
		userDTOs = new ArrayList<UserDTO>();
			List<User> userList = null;
			userList = userdao.getList(User.class);
			for (User user : userList) {
				UserDTO userDTO = UserFactory.setUserList(user);
				userDTOs.add(userDTO);
			}
			return userDTOs;
	}

	@Override
	public UserDTO getUserDTO(long id) throws Exception {
		// TODO Auto-generated method stub
		User user =  userdao.getById(User.class, id);
		UserDTO userDTO = UserFactory.setUserList(user);
		return userDTO;
	}

	@Override
	public void getUserDTOByid(long id) throws Exception {
		// TODO Auto-generated method stub
		User user =  userdao.getById(User.class, id);
		user.setIsactive(false);
		userdao.update(user);
	}
	
}
