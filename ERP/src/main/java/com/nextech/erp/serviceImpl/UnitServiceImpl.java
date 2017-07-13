package com.nextech.erp.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nextech.erp.model.Unit;
import com.nextech.erp.newDTO.UnitDTO;
import com.nextech.erp.service.UnitService;
@Service
public class UnitServiceImpl extends CRUDServiceImpl<Unit> implements UnitService {

	@Override
	public List<UnitDTO> getUnit(long Unit) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
