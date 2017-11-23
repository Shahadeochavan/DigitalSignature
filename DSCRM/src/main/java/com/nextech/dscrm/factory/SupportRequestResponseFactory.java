package com.nextech.dscrm.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.dscrm.dto.SupportDTO;
import com.nextech.dscrm.model.Support;

public class SupportRequestResponseFactory {
	
	public static Support setSupport(SupportDTO supportDTO,HttpServletRequest request){
		Support support =  new Support();
		support.setCompanyName(supportDTO.getCompanyName());
		support.setAddress(supportDTO.getAddress());
		support.setEmailSales(supportDTO.getEmailSales());
		support.setEmailSupport(supportDTO.getEmailSupport());
		support.setTelephone(supportDTO.getTelephone());
		support.setWebsite(supportDTO.getWebsite());
		support.setIsactive(true);
		support.setCreatedBy(Integer.valueOf(request.getAttribute("current_user").toString()));
		return support;
	}
	
	public static Support setSupportUpdate(SupportDTO supportDTO,HttpServletRequest request){
		Support support =  new Support();
		support.setCompanyName(supportDTO.getCompanyName());
		support.setAddress(supportDTO.getAddress());
		support.setEmailSales(supportDTO.getEmailSales());
		support.setEmailSupport(supportDTO.getEmailSupport());
		support.setTelephone(supportDTO.getTelephone());
		support.setIsactive(true);
		support.setUpdatedBy(Integer.valueOf(request.getAttribute("current_user").toString()));
		return support;
	}


}
