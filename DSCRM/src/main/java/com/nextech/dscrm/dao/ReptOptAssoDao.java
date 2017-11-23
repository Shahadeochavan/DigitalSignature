package com.nextech.dscrm.dao;

import java.util.List;

import com.nextech.dscrm.model.Reportoutputassociation;

public interface ReptOptAssoDao extends SuperDao<Reportoutputassociation>{
	
	List<Reportoutputassociation> getListByReportId(long id); 
}
