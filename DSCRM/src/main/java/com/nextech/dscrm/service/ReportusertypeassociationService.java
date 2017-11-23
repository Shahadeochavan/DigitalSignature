package com.nextech.dscrm.service;

import java.util.List;

import com.nextech.dscrm.model.Reportusertypeassociation;

public interface ReportusertypeassociationService extends CRUDService<Reportusertypeassociation>{
	
	public List<Reportusertypeassociation> getReportByUsertype(long usertypeId);

}
