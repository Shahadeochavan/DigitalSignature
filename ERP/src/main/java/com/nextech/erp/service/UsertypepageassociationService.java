package com.nextech.erp.service;

import java.util.List;

import com.nextech.erp.model.Usertypepageassociation;
import com.nextech.erp.newDTO.UserTypePageAssoDTO;

public interface UsertypepageassociationService extends CRUDService<Usertypepageassociation>{
	public List<UserTypePageAssoDTO> getPagesByUsertype(long usertypeId);
	
	public boolean checkPageAccess(long usertypeId,long pageId);
	
	public List<UserTypePageAssoDTO> getUserTypePageAssList() throws Exception;
	
	public UserTypePageAssoDTO getUserTypeDto(long id) throws Exception;
	
	public UserTypePageAssoDTO  deleteUserTypePage(long id)throws Exception;
	
	public UserTypePageAssoDTO addMultipleUserTypePageAsso(UserTypePageAssoDTO userTypePageAssoDTO,String user) throws Exception;
	
	public Usertypepageassociation getUserTypePageAssoByPageIduserTypeId(long pageId,long userTypeId) throws Exception; 
}
