package com.nextech.dscrm.dao;

import java.util.List;

import com.nextech.dscrm.model.Usertypepageassociation;

public interface UsertypepageassociationDao extends SuperDao<Usertypepageassociation>{
	public List<Usertypepageassociation> getPagesByUsertype(long usertypeId);
	
	public boolean checkPageAccess(long usertypeId,long pageId);
	
	public Usertypepageassociation getUserTypePageAssoByPageIduserTypeId(long pageId,long userTypeId) throws Exception; 
}
