package com.nextech.erp.dao;

import java.util.List;

import com.nextech.erp.model.Usertypepageassociation;

public interface UsertypepageassociationDao extends SuperDao<Usertypepageassociation>{
	public List<Usertypepageassociation> getPagesByUsertype(long usertypeId);
	public boolean checkPageAccess(long usertypeId,long pageId);
}
