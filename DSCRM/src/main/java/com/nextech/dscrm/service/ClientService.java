package com.nextech.dscrm.service;

import java.util.List;

import com.nextech.dscrm.model.Client;
import com.nextech.dscrm.newDTO.ClientDTO;

public interface ClientService extends CRUDService<Client>{

	public Client getClientByCompanyName(String companyName) throws Exception;

	public Client getClientByEmail(String email) throws Exception;
	
	public List<ClientDTO> getClientList(List<ClientDTO> clientDTOs) throws  Exception;
	
	public ClientDTO  getClientDTOById(long id) throws Exception;
	
	public ClientDTO deleteClient(long id) throws Exception;
}
