package com.nextech.dscrm.service;

import java.util.List;

import com.nextech.dscrm.model.Reportinputassociation;

public interface ReptInpAssoService extends CRUDService<Reportinputassociation>{
	List<Reportinputassociation> getReportInputParametersByReportId(long id);
}
