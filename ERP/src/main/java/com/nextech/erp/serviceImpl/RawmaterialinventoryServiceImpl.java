package com.nextech.erp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.RawmaterialinventoryDao;
import com.nextech.erp.dto.RMInventoryDTO;
import com.nextech.erp.factory.RMInventoryRequestResponseFactory;
import com.nextech.erp.model.Rawmaterialinventory;
import com.nextech.erp.service.RawmaterialinventoryService;
@Service
public class RawmaterialinventoryServiceImpl extends CRUDServiceImpl<Rawmaterialinventory> implements
		RawmaterialinventoryService {

	@Autowired
	RawmaterialinventoryDao rawmaterialinventoryDao; 
	
	@Override
	public RMInventoryDTO getByRMId(long id) throws Exception {
		Rawmaterialinventory rawmaterialinventory = rawmaterialinventoryDao.getByRMId(id);
		if(rawmaterialinventory==null){
			return null;
		}
		RMInventoryDTO rmInventoryDTO = RMInventoryRequestResponseFactory.setRMInvetoryDTO(rawmaterialinventory);
		return rmInventoryDTO;
	}

	@Override
	public RMInventoryDTO getRMInventoryById(long id) throws Exception {
		
		Rawmaterialinventory rmRawmaterialinventory = rawmaterialinventoryDao.getById(Rawmaterialinventory.class, id);
		if(rmRawmaterialinventory==null){
			return null;
		}
		RMInventoryDTO rmInventoryDTO = RMInventoryRequestResponseFactory.setRMInvetoryDTO(rmRawmaterialinventory);
		return rmInventoryDTO;
	}

	@Override
	public List<RMInventoryDTO> getRMInventoryList() throws Exception {
		
		List<RMInventoryDTO> rmInventoryDTOs =  new ArrayList<RMInventoryDTO>();
		List<Rawmaterialinventory> rawmaterialinventories= rawmaterialinventoryDao.getList(Rawmaterialinventory.class);
		if(rawmaterialinventories==null){
			return null;
		}
		for (Rawmaterialinventory rawmaterialinventory : rawmaterialinventories) {
			RMInventoryDTO rmInventoryDTO = RMInventoryRequestResponseFactory.setRMInvetoryDTO(rawmaterialinventory);
			rmInventoryDTOs.add(rmInventoryDTO);
		}
		return rmInventoryDTOs;
	}

	@Override
	public RMInventoryDTO deleteRMInventory(long id) throws Exception {
		
		Rawmaterialinventory rmRawmaterialinventory = rawmaterialinventoryDao.getById(Rawmaterialinventory.class, id);
		if(rmRawmaterialinventory==null){
			return null;
		}
		rmRawmaterialinventory.setIsactive(false);
		rawmaterialinventoryDao.update(rmRawmaterialinventory);
		RMInventoryDTO rmInventoryDTO = RMInventoryRequestResponseFactory.setRMInvetoryDTO(rmRawmaterialinventory);
		return rmInventoryDTO;
		
	}

}
