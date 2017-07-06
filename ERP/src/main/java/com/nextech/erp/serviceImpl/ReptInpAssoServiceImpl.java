package com.nextech.erp.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.ReptInpAssoDao;
import com.nextech.erp.model.Reportinputassociation;
import com.nextech.erp.service.ReptInpAssoService;
@Service
public class ReptInpAssoServiceImpl extends CRUDServiceImpl<Reportinputassociation> implements ReptInpAssoService {

	@Autowired
	ReptInpAssoDao reptInpAssoDao; 
	
	@Override
	public List<Reportinputassociation> getReportInputParametersByReportId(long id) {
		return reptInpAssoDao.getListByReportId(id);
	}
}