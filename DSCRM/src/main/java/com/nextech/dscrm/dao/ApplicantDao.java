package com.nextech.dscrm.dao;

import java.util.List;

import com.nextech.dscrm.model.Applicant;

public interface ApplicantDao extends SuperDao<Applicant> {
	
	public  List<Applicant>  getApplicantsByClientId(long clientId) throws Exception;

}
