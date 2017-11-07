package com.nextech.erp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.UserDao;
import com.nextech.erp.dto.Mail;
import com.nextech.erp.factory.UserFactory;
import com.nextech.erp.model.User;
import com.nextech.erp.newDTO.NotificationDTO;
import com.nextech.erp.newDTO.NotificationUserAssociatinsDTO;
import com.nextech.erp.newDTO.UserDTO;
import com.nextech.erp.service.MailService;
import com.nextech.erp.service.NotificationService;
import com.nextech.erp.service.NotificationUserAssociationService;
import com.nextech.erp.service.ReportusertypeassociationService;
import com.nextech.erp.service.UserService;
import com.nextech.erp.service.UsertypepageassociationService;

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
