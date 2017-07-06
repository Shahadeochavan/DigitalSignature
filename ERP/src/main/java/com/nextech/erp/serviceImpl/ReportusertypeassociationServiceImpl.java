package com.nextech.erp.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.ReportusertypeassociationDao;
import com.nextech.erp.model.Reportusertypeassociation;
import com.nextech.erp.service.ReportusertypeassociationService;
@Service
public class ReportusertypeassociationServiceImpl extends CRUDServiceImpl<Reportusertypeassociation> implements ReportusertypeassociationService {

	
	@Autowired
	ReportusertypeassociationDao reportusertypeassociationDao;
	@Override
	public List<Reportusertypeassociation> getReportByUsertype(long usertypeId) {
		// TODO Auto-generated method stub
		return reportusertypeassociationDao.getReportByUsertype(usertypeId);
	}

}
