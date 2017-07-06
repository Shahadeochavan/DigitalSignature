package com.nextech.erp.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.ReptOptAssoDao;
import com.nextech.erp.model.Reportoutputassociation;
import com.nextech.erp.service.ReptOptAssoService;
@Service
public class ReptOptAssoServiceImpl extends CRUDServiceImpl<Reportoutputassociation> implements ReptOptAssoService {

	
	@Autowired
	ReptOptAssoDao reptOptAssoDao;

	@Override
	public List<Reportoutputassociation> getReportOutputParametersByReportId(long id) {
		return reptOptAssoDao.getListByReportId(id);
	}
	
}
