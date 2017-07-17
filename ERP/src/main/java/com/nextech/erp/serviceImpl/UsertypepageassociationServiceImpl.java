package com.nextech.erp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.UsertypepageassociationDao;
import com.nextech.erp.factory.UserTypePageAssoFactory;
import com.nextech.erp.model.Usertypepageassociation;
import com.nextech.erp.newDTO.UserTypePageAssoDTO;
import com.nextech.erp.service.UsertypepageassociationService;
@Service
public class UsertypepageassociationServiceImpl extends CRUDServiceImpl<Usertypepageassociation> implements
		UsertypepageassociationService {

	@Autowired
	UsertypepageassociationDao usertypepageassociationDao;
	
	@Override
	public List<Usertypepageassociation> getPagesByUsertype(long usertypeId) {
		return usertypepageassociationDao.getPagesByUsertype(usertypeId);
	}

	@Override
	public boolean checkPageAccess(long usertypeId, long pageId) {
		return usertypepageassociationDao.checkPageAccess(usertypeId, pageId);
	}

	@Override
	public List<UserTypePageAssoDTO> getUserTypePageAssList() throws Exception {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		Usertypepageassociation usertypepageassociation =  usertypepageassociationDao.getById(Usertypepageassociation.class, id);
		UserTypePageAssoDTO userTypePageAssoDTO = UserTypePageAssoFactory.setUserTypePageDTO(usertypepageassociation);
		return userTypePageAssoDTO;
	}

	@Override
	public void deleteUserTypePage(long id) throws Exception {
		// TODO Auto-generated method stub
		Usertypepageassociation usertypepageassociation = usertypepageassociationDao.getById(Usertypepageassociation.class, id);
		usertypepageassociation.setIsactive(false);
		usertypepageassociationDao.update(usertypepageassociation);
		
	}

}
