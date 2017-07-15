package com.nextech.erp.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.dto.SecurityCheckOutDTO;
import com.nextech.erp.model.Securitycheckout;

public class SecurityCheckOutRequestResponseFactory {
	
	public static Securitycheckout setSecrityCheckOut(SecurityCheckOutDTO securityCheckOutDTO,HttpServletRequest request){
		Securitycheckout securitycheckout = new Securitycheckout();
		securitycheckout.setClientname(securityCheckOutDTO.getClientName());
		securitycheckout.setCreateDate(securityCheckOutDTO.getCreateDate());
		securitycheckout.setDescription(securityCheckOutDTO.getDescription());
		securitycheckout.setFirstName(securityCheckOutDTO.getDriverFirstName());
		securitycheckout.setId(securityCheckOutDTO.getId());
		securitycheckout.setIntime(securityCheckOutDTO.getInTime());
		securitycheckout.setInvoice_No(securityCheckOutDTO.getInvoiceNo());
		securitycheckout.setLastName(securityCheckOutDTO.getDriverLastName());
		securitycheckout.setLicence_no(securityCheckOutDTO.getLicenceNo());
		securitycheckout.setOuttime(securityCheckOutDTO.getOutTime());
		securitycheckout.setPoNo(securityCheckOutDTO.getPoNo());
		securitycheckout.setVehicleNo(securityCheckOutDTO.getVehicleNo());
		securitycheckout.setIsactive(true);
		securitycheckout.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		return securitycheckout;
	}
	
	public static Securitycheckout setSecrityCheckOutUpdate(SecurityCheckOutDTO securityCheckOutDTO,HttpServletRequest request){
		Securitycheckout securitycheckout = new Securitycheckout();
		securitycheckout.setClientname(securityCheckOutDTO.getClientName());
		securitycheckout.setCreateDate(securityCheckOutDTO.getCreateDate());
		securitycheckout.setDescription(securityCheckOutDTO.getDescription());
		securitycheckout.setFirstName(securityCheckOutDTO.getDriverFirstName());
		securitycheckout.setId(securityCheckOutDTO.getId());
		securitycheckout.setIntime(securityCheckOutDTO.getInTime());
		securitycheckout.setInvoice_No(securityCheckOutDTO.getInvoiceNo());
		securitycheckout.setLastName(securityCheckOutDTO.getDriverLastName());
		securitycheckout.setLicence_no(securityCheckOutDTO.getLicenceNo());
		securitycheckout.setOuttime(securityCheckOutDTO.getOutTime());
		securitycheckout.setPoNo(securityCheckOutDTO.getPoNo());
		securitycheckout.setVehicleNo(securityCheckOutDTO.getVehicleNo());
		securitycheckout.setIsactive(true);
		securitycheckout.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		return securitycheckout;
	}

}
