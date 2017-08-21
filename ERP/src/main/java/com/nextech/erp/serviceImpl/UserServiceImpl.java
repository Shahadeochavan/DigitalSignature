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
		return userdao.getUserProfileByUserId(id);
	}

	@Override
	public User getUserByFirstNamLastName(String firstName, String lastName) throws Exception {
		return userdao.getUserByFirstNamLastName(firstName, lastName);
	}

	@Override
	public User getUserByNotifictionId(long notificatinId) throws Exception {
		return null;
	}

	@Override
	public User getEmailUserById(long id) throws Exception {
		return userdao.getEmailUserById(id);
	}

	@Override
	public User getUserByContact(String contact) throws Exception {
		return userdao.getUserByContact(contact);
	}

	@Override
	public List<UserDTO> getUserList(List<UserDTO> userDTOs) throws Exception {
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
		User user = userdao.getById(User.class, id);
		UserDTO userDTO = UserFactory.setUserList(user);
		return userDTO;
	}

	@Override
	public void getUserDTOByid(long id) throws Exception {
		User user = userdao.getById(User.class, id);
		user.setIsactive(false);
		userdao.update(user);
	}

	@Override
	public UserDTO getUserDTOByUserId(String userId) throws Exception {
		User user = userdao.getUserByUserId(userId);
		UserDTO userDTO = UserFactory.setUserList(user);
		return userDTO;
	}

	@Override
	public Mail emailNotification(NotificationDTO notificationDTO) throws Exception {
		
		StringBuilder stringBuilderCC = new StringBuilder();
		StringBuilder stringBuilderTO = new StringBuilder();
		StringBuilder stringBuilderBCC = new StringBuilder();

		String prefixCC = "";
		String prefixTO = "";
		String prefixBCC = "";

		String multipleCC = "";
		String multipleBCC = "";
		String multipleTO = "";
		Mail mail = new Mail();
		List<NotificationUserAssociatinsDTO> notificationUserAssociatinsDTOs = notificationUserAssService.getNotificationUserAssociatinsDTOs(notificationDTO.getId());
		
		// By Nikhil on 21/08/2017 : Form comma separated list of notificationuserassociation.getUserId().getId() and fetch all e-mails in single DB call.
		for (NotificationUserAssociatinsDTO notificationuserassociation : notificationUserAssociatinsDTOs) {
			User user = userdao.getById(User.class, notificationuserassociation.getUserId().getId());
			if (notificationuserassociation.getTo()) {
				stringBuilderTO.append(prefixTO);
				prefixTO = ",";
				stringBuilderTO.append(user.getEmail());
				multipleTO = stringBuilderTO.toString();
			} else if (notificationuserassociation.getBcc()) {
				stringBuilderBCC.append(prefixBCC);
				prefixBCC = ",";
				stringBuilderBCC.append(user.getEmail());
				multipleBCC = stringBuilderBCC.toString();
			} else if (notificationuserassociation.getCc()) {
				stringBuilderCC.append(prefixCC);
				prefixCC = ",";
				stringBuilderCC.append(user.getEmail());
				multipleCC = stringBuilderCC.toString();
			}
		}
		mail.setMailTo(multipleTO);
		mail.setMailBcc(multipleBCC);
		mail.setMailCc(multipleCC);
		return mail;
	}
}