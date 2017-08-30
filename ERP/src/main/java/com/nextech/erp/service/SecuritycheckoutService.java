package com.nextech.erp.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.dto.SecurityCheckOutDTO;
import com.nextech.erp.model.Securitycheckout;

public interface SecuritycheckoutService extends CRUDService<Securitycheckout>{
	
	public SecurityCheckOutDTO addSecurityCheckOut(SecurityCheckOutDTO  securityCheckOutDTO,HttpServletRequest request)throws Exception;
	
	public SecurityCheckOutDTO getSecurityCheckOutById(long id) throws Exception;
	
	public List<SecurityCheckOutDTO> getSecurityCheckOutList() throws Exception;
	
	public SecurityCheckOutDTO deleteSecurityCheckOut(long id)throws Exception;

}
