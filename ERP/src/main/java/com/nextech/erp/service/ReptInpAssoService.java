package com.nextech.erp.service;

import java.util.List;

import com.nextech.erp.model.Reportinputassociation;

public interface ReptInpAssoService extends CRUDService<Reportinputassociation>{
	List<Reportinputassociation> getReportInputParametersByReportId(long id);
}
