package com.nextech.erp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.ClientDao;
import com.nextech.erp.factory.ClientFactory;
import com.nextech.erp.model.Client;
import com.nextech.erp.newDTO.ClientDTO;
import com.nextech.erp.service.ClientService;
@Service
public class ClientServiceImpl extends CRUDServiceImpl<Client> implements ClientService{

	@Autowired
	ClientDao clientDao;

	@Override
	public Client getClientByCompanyName(String companyName) throws Exception {
		return clientDao.getClientByCompanyName(companyName);
	}

	@Override
	public Client getClientByEmail(String email) throws Exception {
		return clientDao.getClientByEmail(email);
	}

	@Override
	public List<ClientDTO> getClientList(List<ClientDTO> clientDTOs)
			throws Exception {
		// TODO Auto-generated method stub
		clientDTOs =  new ArrayList<ClientDTO>();
		List<Client> clList = clientDao.getList(Client.class);
		for (Client client : clList) {
			ClientDTO clientDTO = ClientFactory.setClientDTO(client);
			clientDTOs.add(clientDTO);
		}
		return clientDTOs;
	}

	@Override
	public ClientDTO getClientDTOById(long id) throws Exception {
		// TODO Auto-generated method stub
		Client client = clientDao.getById(Client.class, id);
		ClientDTO clientDTO = ClientFactory.setClientDTO(client);
		return clientDTO;
	}

	@Override
	public void deleteClient(long id) throws Exception {
		// TODO Auto-generated method stub
		Client client = clientDao.getById(Client.class, id);
		client.setIsactive(false);
		clientDao.update(client);
		
	}
}

