package com.nextech.erp.dao;

import com.nextech.erp.model.Statustransition;

public interface StatustransitionDao extends SuperDao<Statustransition>{

	public Statustransition getStatustransitionByEmail(String email) throws Exception;
}
