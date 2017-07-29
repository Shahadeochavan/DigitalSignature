package com.nextech.erp.service;

import java.util.List;

import com.nextech.erp.dto.RMInventoryDTO;
import com.nextech.erp.model.Rawmaterialinventory;

public interface RawmaterialinventoryService extends CRUDService<Rawmaterialinventory>{
	
	public RMInventoryDTO getByRMId(long id) throws Exception;
	
	public RMInventoryDTO getRMInventoryById(long id) throws Exception;
	
	public List<RMInventoryDTO> getRMInventoryList() throws Exception;
	
	public void deleteRMInventory(long id) throws Exception;
	
}