package com.nextech.dscrm.dao;

import java.util.List;

import com.nextech.dscrm.model.Reportinputassociation;

public interface ReptInpAssoDao extends SuperDao<Reportinputassociation>{
	
	List<Reportinputassociation> getListByReportId(long id); 
}
