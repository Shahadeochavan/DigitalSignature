package com.nextech.erp.dao;

import com.nextech.erp.model.Rawmaterialinventory;

public interface RawmaterialinventoryDao extends SuperDao<Rawmaterialinventory>{
	public Rawmaterialinventory getByRMId(long id) throws Exception;
}
