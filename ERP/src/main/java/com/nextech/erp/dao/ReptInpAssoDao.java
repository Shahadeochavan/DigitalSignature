package com.nextech.erp.dao;

import java.util.List;

import com.nextech.erp.model.Reportinputassociation;

public interface ReptInpAssoDao extends SuperDao<Reportinputassociation>{
	List<Reportinputassociation> getListByReportId(long id); 
}
