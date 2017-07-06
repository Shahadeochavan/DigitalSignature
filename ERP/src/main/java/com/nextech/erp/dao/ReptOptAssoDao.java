package com.nextech.erp.dao;

import java.util.List;

import com.nextech.erp.model.Reportoutputassociation;

public interface ReptOptAssoDao extends SuperDao<Reportoutputassociation>{
	List<Reportoutputassociation> getListByReportId(long id); 
}
