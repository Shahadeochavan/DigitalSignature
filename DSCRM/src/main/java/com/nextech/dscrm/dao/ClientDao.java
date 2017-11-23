package com.nextech.dscrm.dao;

import com.nextech.dscrm.model.Client;

public interface ClientDao extends SuperDao<Client>{

	public Client getClientByCompanyName(String companyName) throws Exception;

	public Client getClientByEmail(String email) throws Exception;
}
