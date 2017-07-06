package com.nextech.erp.service;

import com.nextech.erp.model.Statustransition;

public interface StatustransitionService extends CRUDService<Statustransition>{
	
	public Statustransition getStatustransitionByEmail(String email) throws Exception;
}

