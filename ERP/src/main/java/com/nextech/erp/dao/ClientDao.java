package com.nextech.erp.dao;

import com.nextech.erp.model.Client;

public interface ClientDao extends SuperDao<Client>{

	public Client getClientByCompanyName(String companyName) throws Exception;

	public Client getClientByEmail(String email) throws Exception;
}
