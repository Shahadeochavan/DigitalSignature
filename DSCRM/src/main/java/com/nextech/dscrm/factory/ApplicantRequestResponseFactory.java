package com.nextech.dscrm.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.dscrm.dto.ApplicantDTO;
import com.nextech.dscrm.model.Applicant;

public class ApplicantRequestResponseFactory {
	
	public static Applicant setApplicant(ApplicantDTO applicantDTO ,HttpServletRequest request){
		Applicant applicant = new Applicant();
		applicant.setApplicantId(applicantDTO.getCustomerId());
		applicant.setApplicantName(applicantDTO.getFullName());
		applicant.setCreateDate(applicantDTO.getCreateDate());
		applicant.setEmail(applicantDTO.getEmailId());
		applicant.setFormSubmissin(applicantDTO.getFormSubmission());
		applicant.setMobileNumber(applicantDTO.getMobileNumber());
		applicant.setPaidUnPaid(applicantDTO.getPaidUnpaid());
		applicant.setPrice(applicantDTO.getPrice());
		applicant.setValidity(applicantDTO.getValidity());
		applicant.setProduct(applicantDTO.getProduct());
		applicant.setClient(applicantDTO.getClient());
		applicant.setIsactive(true);
		applicant.setCreatedBy((request.getAttribute("current_user").toString()));
		return applicant;
	}
	
	public static Applicant setApplicantUpdate(ApplicantDTO applicantDTO ,HttpServletRequest request){
		Applicant applicant = new Applicant();
		applicant.setApplicantId(applicantDTO.getCustomerId());
		applicant.setId(applicantDTO.getId());
		applicant.setApplicantName(applicantDTO.getFullName());
		applicant.setCreateDate(applicantDTO.getCreateDate());
		applicant.setEmail(applicantDTO.getEmailId());
		applicant.setFormSubmissin(applicantDTO.getFormSubmission());
		applicant.setMobileNumber(applicantDTO.getMobileNumber());
		applicant.setPaidUnPaid(applicantDTO.getPaidUnpaid());
		applicant.setPrice(applicantDTO.getPrice());
		applicant.setValidity(applicantDTO.getValidity());
		applicant.setIsactive(true);
		applicant.setProduct(applicantDTO.getProduct());
		applicant.setClient(applicantDTO.getClient());
		applicant.setUpdatedBy((request.getAttribute("current_user").toString()));
		return applicant;
	}

}
