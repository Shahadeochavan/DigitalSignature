package com.nextech.erp.serviceImpl;

import java.util.ArrayList;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.UnitDao;
import com.nextech.erp.factory.UnitFactory;
import com.nextech.erp.model.Unit;
import com.nextech.erp.newDTO.UnitDTO;
import com.nextech.erp.service.UnitService;
@Service
public class UnitServiceImpl extends CRUDServiceImpl<Unit> implements UnitService {
	
	@Autowired
	UnitDao unitDao;

	@Override
	public List<UnitDTO> getUnitList() throws Exception {
		
		List<UnitDTO> unitDTOs = new ArrayList<UnitDTO>();
		List<Unit> units = unitDao.getList(Unit.class);
		if(units.isEmpty()){
			return null;
		}
		for (Unit unit : units) {
			UnitDTO unitDTO = UnitFactory.setUnitDTO(unit);
			unitDTOs.add(unitDTO);
		}
		return unitDTOs;
	}

	@Override
	public UnitDTO getUnitByID(long id) throws Exception {
		
		Unit unit = unitDao.getById(Unit.class, id);
		if(unit==null){
			return null;
		}
		UnitDTO unitDTO = UnitFactory.setUnitDTO(unit);
		return unitDTO;
	}

	@Override
	public UnitDTO deleteUnit(long id) throws Exception {
		
		Unit unit = unitDao.getById(Unit.class, id);
		if(unit==null){
			return null;
		}
		unit.setIsactive(false);
		unitDao.update(unit);
		UnitDTO unitDTO = UnitFactory.setUnitDTO(unit);
		return unitDTO;
	}

	@Override
	public Unit getUnitByName(String name) throws Exception {
		// TODO Auto-generated method stub
		return unitDao.getUnitByName(name);
	}

}
