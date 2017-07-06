package com.nextech.erp.service;

import java.util.List;

import com.nextech.erp.model.Reportusertypeassociation;

public interface ReportusertypeassociationService extends CRUDService<Reportusertypeassociation>{
	
	public List<Reportusertypeassociation> getReportByUsertype(long usertypeId);

}
