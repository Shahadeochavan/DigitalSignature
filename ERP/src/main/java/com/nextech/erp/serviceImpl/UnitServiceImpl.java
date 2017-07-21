package com.nextech.erp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.UniqueConstraint;

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
	public List<UnitDTO> getUnit(long Unit) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UnitDTO> getUnitList() throws Exception {
		// TODO Auto-generated method stub
		List<UnitDTO> unitDTOs = new ArrayList<UnitDTO>();
		List<Unit> units = unitDao.getList(Unit.class);
		for (Unit unit : units) {
			UnitDTO unitDTO = UnitFactory.setUnitDTO(unit);
			unitDTOs.add(unitDTO);
		}
		return unitDTOs;
	}

	@Override
	public UnitDTO getUnitByID(long id) throws Exception {
		// TODO Auto-generated method stub
		Unit unit = unitDao.getById(Unit.class, id);
		UnitDTO unitDTO = UnitFactory.setUnitDTO(unit);
		return unitDTO;
	}

	@Override
	public void deleteUnit(long id) throws Exception {
		// TODO Auto-generated method stub
		Unit unit = unitDao.getById(Unit.class, id);
		unit.setIsactive(false);
		unitDao.update(unit);
	}

}
