package com.nextech.dscrm.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.dscrm.dao.ApplicantDao;
import com.nextech.dscrm.model.Applicant;
import com.nextech.dscrm.service.ApplicantService;

@Service
public class ApplicantServiceImpl extends CRUDServiceImpl<Applicant> implements ApplicantService{

	@Autowired
	ApplicantDao applicantDao;
	@Override
	public List<Applicant> getApplicantsByClientId(long clientId)
			throws Exception {
		// TODO Auto-generated method stub
		return applicantDao.getApplicantsByClientId(clientId);
	}

}
