package com.nextech.erp.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.dto.SecurityCheckOutDTO;
import com.nextech.erp.model.Securitycheckout;

public class SecurityCheckOutRequestResponseFactory {
	
	public static Securitycheckout setSecurityCheckOut(SecurityCheckOutDTO securityCheckOutDTO,HttpServletRequest request){
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
	
	public static Securitycheckout setSecurityCheckOutUpdate(SecurityCheckOutDTO securityCheckOutDTO,HttpServletRequest request){
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
	
	public static SecurityCheckOutDTO setSecurityCheckOutDTO(Securitycheckout  securitycheckout){
		SecurityCheckOutDTO securityCheckOutDTO =  new SecurityCheckOutDTO();
		securityCheckOutDTO.setClientName(securitycheckout.getClientname());
		securityCheckOutDTO.setCreateDate(securitycheckout.getCreateDate());
		securityCheckOutDTO.setDescription(securitycheckout.getDescription());
		securityCheckOutDTO.setDriverFirstName(securitycheckout.getFirstName());
		securityCheckOutDTO.setId(securitycheckout.getId());
		securityCheckOutDTO.setInTime(securitycheckout.getIntime());
		securityCheckOutDTO.setInvoiceNo(securitycheckout.getLicence_no());
		securityCheckOutDTO.setDriverLastName(securitycheckout.getLastName());
		securityCheckOutDTO.setLicenceNo(securitycheckout.getLicence_no());
		securityCheckOutDTO.setOutTime(securitycheckout.getOuttime());
		securityCheckOutDTO.setPoNo(securitycheckout.getPoNo());
		securityCheckOutDTO.setVehicleNo(securitycheckout.getVehicleNo());
		securityCheckOutDTO.setActive(true);
		return securityCheckOutDTO;
	} 

}
