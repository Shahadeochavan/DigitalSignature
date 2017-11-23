package com.nextech.dscrm.dao;

import com.nextech.dscrm.model.Statustransition;

public interface StatustransitionDao extends SuperDao<Statustransition>{

	public Statustransition getStatustransitionByEmail(String email) throws Exception;
}
