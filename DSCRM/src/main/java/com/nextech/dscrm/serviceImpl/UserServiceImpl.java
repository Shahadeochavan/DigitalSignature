package com.nextech.dscrm.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.nextech.dscrm.dao.UserDao;
import com.nextech.dscrm.factory.UserFactory;
import com.nextech.dscrm.model.User;
import com.nextech.dscrm.newDTO.UserDTO;
import com.nextech.dscrm.service.MailService;
import com.nextech.dscrm.service.NotificationService;
import com.nextech.dscrm.service.NotificationUserAssociationService;
import com.nextech.dscrm.service.ReportusertypeassociationService;
import com.nextech.dscrm.service.UserService;
import com.nextech.dscrm.service.UsertypepageassociationService;

@Service
@Qualifier("userServiceImpl")

public class UserServiceImpl extends CRUDServiceImpl<User> implements UserService {

	@Autowired
	UserDao userdao;

	@Autowired
	UsertypepageassociationService usertypepageassociationService;

	@Autowired
	NotificationUserAssociationService notificationUserAssService;

	@Autowired
	NotificationService notificationService;

	@Autowired
	ReportusertypeassociationService reportusertypeassociationService;

	@Autowired
	MailService mailService;
	
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
	public List<UserDTO> getUserList(List<UserDTO> userDTOs) throws Exception {
		userDTOs = new ArrayList<UserDTO>();
		List<User> userList = null;
		userList = userdao.getList(User.class);
		if(userList==null){
			return null;
		}
		for (User user : userList) {
			UserDTO userDTO = UserFactory.setUserList(user);
			userDTOs.add(userDTO);
		}
		return userDTOs;
	}

	@Override
	public UserDTO getUserDTO(long id) throws Exception {
		User user = userdao.getById(User.class, id);
		if(user==null){
			return null;
		}
		UserDTO userDTO = UserFactory.setUserList(user);
		return userDTO;
	}

	@Override
	public UserDTO deleteUser(long id) throws Exception {
		User user = userdao.getById(User.class, id);
		if(user==null){
			return null;
		}
		user.setIsactive(false);
		userdao.update(user);
		UserDTO userDTO = UserFactory.setUserList(user);
		return userDTO;
	}
}
