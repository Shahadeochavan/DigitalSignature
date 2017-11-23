package com.nextech.dscrm.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.nextech.dscrm.dto.SecurityCheckOutDTO;
import com.nextech.dscrm.model.Securitycheckout;

public interface SecuritycheckoutService extends CRUDService<Securitycheckout>{
	
	public SecurityCheckOutDTO addSecurityCheckOut(SecurityCheckOutDTO  securityCheckOutDTO,HttpServletRequest request)throws Exception;
	
	public SecurityCheckOutDTO getSecurityCheckOutById(long id) throws Exception;
	
	public List<SecurityCheckOutDTO> getSecurityCheckOutList() throws Exception;
	
	public SecurityCheckOutDTO deleteSecurityCheckOut(long id)throws Exception;

}
