package com.nextech.dscrm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.dscrm.dao.ReportusertypeassociationDao;
import com.nextech.dscrm.model.Reportusertypeassociation;
import com.nextech.dscrm.service.ReportusertypeassociationService;
@Service
public class ReportusertypeassociationServiceImpl extends CRUDServiceImpl<Reportusertypeassociation> implements ReportusertypeassociationService {

	
	@Autowired
	ReportusertypeassociationDao reportusertypeassociationDao;
	@Override
	public List<Reportusertypeassociation> getReportByUsertype(long usertypeId) {
		
		return reportusertypeassociationDao.getReportByUsertype(usertypeId);
	}

}
