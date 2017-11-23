package com.nextech.dscrm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.dscrm.dao.ReptInpAssoDao;
import com.nextech.dscrm.model.Reportinputassociation;
import com.nextech.dscrm.service.ReptInpAssoService;
@Service
public class ReptInpAssoServiceImpl extends CRUDServiceImpl<Reportinputassociation> implements ReptInpAssoService {

	@Autowired
	ReptInpAssoDao reptInpAssoDao; 
	
	@Override
	public List<Reportinputassociation> getReportInputParametersByReportId(long id) {
		return reptInpAssoDao.getListByReportId(id);
	}
}