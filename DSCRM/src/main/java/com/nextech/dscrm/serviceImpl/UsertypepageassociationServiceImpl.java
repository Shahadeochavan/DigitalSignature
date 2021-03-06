package com.nextech.dscrm.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.dscrm.dao.PageDao;
import com.nextech.dscrm.dao.UserTypeDao;
import com.nextech.dscrm.dao.UsertypepageassociationDao;
import com.nextech.dscrm.factory.UserTypePageAssoFactory;
import com.nextech.dscrm.model.Page;
import com.nextech.dscrm.model.Usertype;
import com.nextech.dscrm.model.Usertypepageassociation;
import com.nextech.dscrm.newDTO.UserTypePageAssoDTO;
import com.nextech.dscrm.newDTO.UserTypePageAssoPart;
import com.nextech.dscrm.service.UsertypepageassociationService;
@Service
public class UsertypepageassociationServiceImpl extends CRUDServiceImpl<Usertypepageassociation> implements
		UsertypepageassociationService {

	@Autowired
	UsertypepageassociationDao usertypepageassociationDao;
	
	@Autowired
	PageDao pageDao;
	
	@Autowired
	UserTypeDao userTypeDao;
	
	@Override
	public List<UserTypePageAssoDTO> getPagesByUsertype(long usertypeId) {
		List<UserTypePageAssoDTO> userTypePageAssoDTOs = new ArrayList<UserTypePageAssoDTO>();
		List<Usertypepageassociation> usertypepageassociations = usertypepageassociationDao.getPagesByUsertype(usertypeId);
		for (Usertypepageassociation usertypepageassociation : usertypepageassociations) {
			UserTypePageAssoDTO userTypePageAssoDTO =  UserTypePageAssoFactory.setUserTypePageDTO(usertypepageassociation);
			userTypePageAssoDTOs.add(userTypePageAssoDTO);
		}
		return userTypePageAssoDTOs;
	}

	@Override
	public boolean checkPageAccess(long usertypeId, long pageId) {
		return usertypepageassociationDao.checkPageAccess(usertypeId, pageId);
	}

	@Override
	public List<UserTypePageAssoDTO> getUserTypePageAssList() throws Exception {
		
		List<UserTypePageAssoDTO> userTypePageAssoDTOs = new ArrayList<UserTypePageAssoDTO>();
		List<Usertypepageassociation> usertypepageassociations = usertypepageassociationDao.getList(Usertypepageassociation.class);
		for (Usertypepageassociation usertypepageassociation : usertypepageassociations) {
			UserTypePageAssoDTO userTypePageAssoDTO =  UserTypePageAssoFactory.setUserTypePageDTO(usertypepageassociation);
			userTypePageAssoDTOs.add(userTypePageAssoDTO);
		}
		return userTypePageAssoDTOs;
	}

	@Override
	public UserTypePageAssoDTO getUserTypeDto(long id) throws Exception {
		Usertypepageassociation usertypepageassociation =  usertypepageassociationDao.getById(Usertypepageassociation.class, id);
		UserTypePageAssoDTO userTypePageAssoDTO = UserTypePageAssoFactory.setUserTypePageDTO(usertypepageassociation);
		return userTypePageAssoDTO;
	}

	@Override
	public UserTypePageAssoDTO deleteUserTypePage(long id) throws Exception {
		Usertypepageassociation usertypepageassociation = usertypepageassociationDao.getById(Usertypepageassociation.class, id);
		if(usertypepageassociation==null){
			return null;
		}
		usertypepageassociation.setIsactive(false);
		usertypepageassociationDao.update(usertypepageassociation);
		UserTypePageAssoDTO userTypePageAssoDTO = UserTypePageAssoFactory.setUserTypePageDTO(usertypepageassociation);
		return userTypePageAssoDTO;
		
	}

	@Override
	public UserTypePageAssoDTO addMultipleUserTypePageAsso(UserTypePageAssoDTO userTypePageAssoDTO, String currentUser)
			throws Exception {
		for(UserTypePageAssoPart userTypePageAssoPart : userTypePageAssoDTO.getUserTypePageAssoParts()){
			Usertypepageassociation usertypepageassociation =	 UserTypePageAssoFactory.setUserTypePageAss(userTypePageAssoDTO);
			 usertypepageassociation =  setMultiplePage(userTypePageAssoPart);
			usertypepageassociation.setUsertype(userTypeDao.getById(Usertype.class, userTypePageAssoDTO.getUsertypeId().getId()));
			usertypepageassociation.setCreatedBy(Long.parseLong(currentUser));
			usertypepageassociationDao.add(usertypepageassociation);
		}
		return userTypePageAssoDTO;
	}
	
	private Usertypepageassociation setMultiplePage(UserTypePageAssoPart userTypePageAssoPart) throws Exception {
		Usertypepageassociation usertypepageassociation = new Usertypepageassociation();
		usertypepageassociation.setPage(pageDao.getById(Page.class, userTypePageAssoPart.getPageId().getId()));
		usertypepageassociation.setIsactive(true);
		return usertypepageassociation;
	}

	@Override
	public Usertypepageassociation getUserTypePageAssoByPageIduserTypeId(
			long pageId, long userTypeId) throws Exception {
		return usertypepageassociationDao.getUserTypePageAssoByPageIduserTypeId(pageId, userTypeId);
	}

}
