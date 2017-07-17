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
	
	public static ClientDTO setClientDTO(Client  client){
		ClientDTO clientDTO =  new ClientDTO();
		clientDTO.setId(client.getId());
		clientDTO.setAddress(client.getAddress());
		clientDTO.setCommisionerate(client.getCommisionerate());
		clientDTO.setCompanyName(client.getCompanyname());
		clientDTO.setContactNumber(client.getContactnumber());
		clientDTO.setContactPersonName(client.getContactpersonname());
		clientDTO.setCst(client.getCst());
		clientDTO.setCustomerEccNumber(client.getCustomerEccNumber());
		clientDTO.setDescription(client.getDescription());
		clientDTO.setDivision(client.getDivision());
		clientDTO.setEmailId(client.getEmailid());
		clientDTO.setRenge(client.getRenge());
		clientDTO.setVatNo(client.getVatNo());
		clientDTO.setActive(true);
		return clientDTO;
	}
	
}
