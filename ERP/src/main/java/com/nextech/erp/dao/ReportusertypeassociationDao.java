package com.nextech.erp.dao;

import java.util.List;

import com.nextech.erp.model.Reportusertypeassociation;

public interface ReportusertypeassociationDao  extends SuperDao<Reportusertypeassociation>{
	
	public List<Reportusertypeassociation> getReportByUsertype(long usertypeId);

}
