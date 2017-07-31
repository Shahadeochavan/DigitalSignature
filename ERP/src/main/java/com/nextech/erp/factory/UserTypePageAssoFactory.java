package com.nextech.erp.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.model.Usertypepageassociation;
import com.nextech.erp.newDTO.UserTypePageAssoDTO;

public class UserTypePageAssoFactory {

	public static  Usertypepageassociation setUserTypePageAss(UserTypePageAssoDTO userTypePageAssoDTO){
		Usertypepageassociation usertypepageassociation = new Usertypepageassociation();
		usertypepageassociation.setId(userTypePageAssoDTO.getId());
		usertypepageassociation.setPage(userTypePageAssoDTO.getPage());
		usertypepageassociation.setUsertype(userTypePageAssoDTO.getUsertypeId());
		usertypepageassociation.setIsactive(true);
		return usertypepageassociation;
	}
	public static  Usertypepageassociation setUserTypePageAssUpdate(UserTypePageAssoDTO userTypePageAssoDTO, HttpServletRequest request){
		Usertypepageassociation usertypepageassociation = new Usertypepageassociation();
		usertypepageassociation.setId(userTypePageAssoDTO.getId());
		usertypepageassociation.setPage(userTypePageAssoDTO.getPage());
		usertypepageassociation.setUsertype(userTypePageAssoDTO.getUsertypeId());
		usertypepageassociation.setIsactive(true);
		usertypepageassociation.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		return usertypepageassociation;
	}
	public static UserTypePageAssoDTO setUserTypePageDTO(Usertypepageassociation usertypepageassociation){
		UserTypePageAssoDTO userTypePageAssoDTO = new UserTypePageAssoDTO();
		userTypePageAssoDTO.setId(usertypepageassociation.getId());
		userTypePageAssoDTO.setPage(usertypepageassociation.getPage());
		userTypePageAssoDTO.setUsertypeId(usertypepageassociation.getUsertype());
		userTypePageAssoDTO.setActive(true);
		return userTypePageAssoDTO;
	}
	
}
