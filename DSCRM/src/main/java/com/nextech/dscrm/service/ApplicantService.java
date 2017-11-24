package com.nextech.dscrm.service;

import java.util.List;

import com.nextech.dscrm.model.Applicant;

public interface ApplicantService extends CRUDService<Applicant >{
	
	public  List<Applicant>  getApplicantsByClientId(long clientId) throws Exception;

}
