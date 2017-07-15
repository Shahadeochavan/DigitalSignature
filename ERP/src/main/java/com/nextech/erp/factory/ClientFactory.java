package com.nextech.erp.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.model.Client;
import com.nextech.erp.newDTO.ClientDTO;

public class ClientFactory {

	public static Client setClient( ClientDTO clientDTO,HttpServletRequest request){
		Client client = new Client();
		client.setId(clientDTO.getId());
		client.setAddress(clientDTO.getAddress());
		client.setCommisionerate(clientDTO.getCommisionerate());
		client.setCompanyname(clientDTO.getCompanyName());
		client.setContactnumber(clientDTO.getContactNumber());
		client.setContactpersonname(clientDTO.getContactPersonName());
		client.setCst(clientDTO.getCst());
		client.setCustomerEccNumber(clientDTO.getCustomerEccNumber());
		client.setDescription(clientDTO.getDescription());
		client.setDivision(clientDTO.getDivision());
		client.setEmailid(clientDTO.getEmailId());
		client.setRenge(clientDTO.getRenge());
		client.setVatNo(clientDTO.getVatNo());
		client.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		client.setIsactive(true);
		return client;
	}
	
	public static Client setClientUpdate( ClientDTO clientDTO,HttpServletRequest request){
		Client client = new Client();
		client.setId(clientDTO.getId());
		client.setAddress(clientDTO.getAddress());
		client.setCommisionerate(clientDTO.getCommisionerate());
		client.setCompanyname(clientDTO.getCompanyName());
		client.setContactnumber(clientDTO.getContactNumber());
		client.setContactpersonname(clientDTO.getContactPersonName());
		client.setCst(clientDTO.getCst());
		client.setCustomerEccNumber(clientDTO.getCustomerEccNumber());
		client.setDescription(clientDTO.getDescription());
		client.setDivision(clientDTO.getDivision());
		client.setEmailid(clientDTO.getEmailId());
		client.setRenge(clientDTO.getRenge());
		client.setVatNo(clientDTO.getVatNo());
		client.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		client.setIsactive(true);
		return client;
	}
	
}
