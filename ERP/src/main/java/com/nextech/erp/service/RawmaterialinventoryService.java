package com.nextech.erp.service;

import com.nextech.erp.model.Rawmaterialinventory;

public interface RawmaterialinventoryService extends CRUDService<Rawmaterialinventory>{
	public Rawmaterialinventory getByRMId(long id) throws Exception;
	
}