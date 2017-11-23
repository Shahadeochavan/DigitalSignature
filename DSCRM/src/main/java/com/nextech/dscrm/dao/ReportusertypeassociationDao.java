package com.nextech.dscrm.dao;

import java.util.List;

import com.nextech.dscrm.model.Reportusertypeassociation;

public interface ReportusertypeassociationDao  extends SuperDao<Reportusertypeassociation>{
	
	public List<Reportusertypeassociation> getReportByUsertype(long usertypeId);

}
