package com.nextech.erp.service;

import java.util.List;

import com.nextech.erp.model.Reportoutputassociation;

public interface ReptOptAssoService extends CRUDService<Reportoutputassociation>{
	List<Reportoutputassociation> getReportOutputParametersByReportId(long id);
}
