package com.nextech.dscrm.service;

import java.util.List;

import com.nextech.dscrm.model.Reportoutputassociation;

public interface ReptOptAssoService extends CRUDService<Reportoutputassociation>{
	List<Reportoutputassociation> getReportOutputParametersByReportId(long id);
}
