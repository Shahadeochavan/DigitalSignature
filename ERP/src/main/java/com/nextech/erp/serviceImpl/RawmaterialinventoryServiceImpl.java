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
	public Rawmaterialinventory getByRMId(long id) throws Exception {
		return rawmaterialinventoryDao.getByRMId(id);
	}

	@Override
	public RMInventoryDTO getRMInventoryById(long id) throws Exception {
		// TODO Auto-generated method stub
		Rawmaterialinventory rmRawmaterialinventory = rawmaterialinventoryDao.getById(Rawmaterialinventory.class, id);
		RMInventoryDTO rmInventoryDTO = RMInventoryRequestResponseFactory.setRMInvetoryDTO(rmRawmaterialinventory);
		return rmInventoryDTO;
	}

	@Override
	public List<RMInventoryDTO> getRMInventoryList() throws Exception {
		// TODO Auto-generated method stub
		List<RMInventoryDTO> rmInventoryDTOs =  new ArrayList<RMInventoryDTO>();
		List<Rawmaterialinventory> rawmaterialinventories= rawmaterialinventoryDao.getList(Rawmaterialinventory.class);
		for (Rawmaterialinventory rawmaterialinventory : rawmaterialinventories) {
			RMInventoryDTO rmInventoryDTO = RMInventoryRequestResponseFactory.setRMInvetoryDTO(rawmaterialinventory);
			rmInventoryDTOs.add(rmInventoryDTO);
		}
		return rmInventoryDTOs;
	}

	@Override
	public void deleteRMInventory(long id) throws Exception {
		// TODO Auto-generated method stub
		Rawmaterialinventory rmRawmaterialinventory = rawmaterialinventoryDao.getById(Rawmaterialinventory.class, id);
		rmRawmaterialinventory.setIsactive(false);
		rawmaterialinventoryDao.update(rmRawmaterialinventory);
		
	}

}
