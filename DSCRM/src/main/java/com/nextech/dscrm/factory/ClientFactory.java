package com.nextech.dscrm.factory;

import javax.servlet.http.HttpServletRequest;

import com.nextech.dscrm.model.Client;
import com.nextech.dscrm.newDTO.ClientDTO;

public class ClientFactory {

	public static Client setClient( ClientDTO clientDTO,HttpServletRequest request){
		Client client = new Client();
		client.setId(clientDTO.getId());
		client.setAddress(clientDTO.getAddress());
		client.setCompanyname(clientDTO.getCompanyName());
		client.setContactnumber(clientDTO.getContactNumber());
		client.setContactpersonname(clientDTO.getContactPersonName());
		client.setDescription(clientDTO.getDescription());
		client.setEmailid(clientDTO.getEmailId());
		client.setCreatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		client.setIsactive(true);
		return client;
	}
	
	public static Client setClientUpdate( ClientDTO clientDTO,HttpServletRequest request){
		Client client = new Client();
		client.setId(clientDTO.getId());
		client.setAddress(clientDTO.getAddress());
		client.setCompanyname(clientDTO.getCompanyName());
		client.setContactnumber(clientDTO.getContactNumber());
		client.setContactpersonname(clientDTO.getContactPersonName());
		client.setDescription(clientDTO.getDescription());
		client.setEmailid(clientDTO.getEmailId());
		client.setUpdatedBy(Long.parseLong(request.getAttribute("current_user").toString()));
		client.setIsactive(true);
		return client;
	}
	
	public static ClientDTO setClientDTO(Client  client){
		ClientDTO clientDTO =  new ClientDTO();
		clientDTO.setId(client.getId());
		clientDTO.setAddress(client.getAddress());
		clientDTO.setCompanyName(client.getCompanyname());
		clientDTO.setContactNumber(client.getContactnumber());
		clientDTO.setContactPersonName(client.getContactpersonname());
		clientDTO.setDescription(client.getDescription());
		clientDTO.setEmailId(client.getEmailid());
		clientDTO.setActive(true);
		return clientDTO;
	}
	
}
