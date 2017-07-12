package com.nextech.erp.service;

import com.nextech.erp.model.Client;

public interface ClientService extends CRUDService<Client>{

	public Client getClientByCompanyName(String companyName) throws Exception;

	public Client getClientByEmail(String email) throws Exception;
}
