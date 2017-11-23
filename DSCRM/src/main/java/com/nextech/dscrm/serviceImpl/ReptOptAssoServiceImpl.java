package com.nextech.dscrm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.dscrm.dao.ReptOptAssoDao;
import com.nextech.dscrm.model.Reportoutputassociation;
import com.nextech.dscrm.service.ReptOptAssoService;
@Service
public class ReptOptAssoServiceImpl extends CRUDServiceImpl<Reportoutputassociation> implements ReptOptAssoService {

	
	@Autowired
	ReptOptAssoDao reptOptAssoDao;

	@Override
	public List<Reportoutputassociation> getReportOutputParametersByReportId(long id) {
		return reptOptAssoDao.getListByReportId(id);
	}
	
}
