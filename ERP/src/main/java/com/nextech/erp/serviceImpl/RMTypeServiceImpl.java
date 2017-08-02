package com.nextech.erp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.RMTypeDao;
import com.nextech.erp.factory.RMTypeRequestResponseFactory;
import com.nextech.erp.model.Rmtype;
import com.nextech.erp.newDTO.RMTypeDTO;
import com.nextech.erp.service.RMTypeService;

@Service
public class RMTypeServiceImpl extends CRUDServiceImpl<Rmtype> implements RMTypeService{
	
	@Autowired
	RMTypeDao rMTypeDao;

	@Override
	public List<RMTypeDTO> getRMTypeList() throws Exception {
		// TODO Auto-generated method stub
		List<RMTypeDTO> rmTypeDTOs = new ArrayList<RMTypeDTO>();
		List<Rmtype> rmtypes = rMTypeDao.getList(Rmtype.class);
		for (Rmtype rmtype : rmtypes) {
			RMTypeDTO rmTypeDTO = RMTypeRequestResponseFactory.setRMTypeDTO(rmtype);
			rmTypeDTOs.add(rmTypeDTO);
		}
		return rmTypeDTOs;
	}

	@Override
	public RMTypeDTO getRMTypeById(long id) throws Exception {
		// TODO Auto-generated method stub
		Rmtype rmtype  = rMTypeDao.getById(Rmtype.class, id);
		RMTypeDTO rmTypeDTO = RMTypeRequestResponseFactory.setRMTypeDTO(rmtype);
		return rmTypeDTO;
	}

	@Override
	public void deleteRMType(long id) throws Exception {
		// TODO Auto-generated method stub
		Rmtype rmtype  = rMTypeDao.getById(Rmtype.class, id);
		rmtype.setIsactive(false);
		rMTypeDao.update(rmtype);
		
	}

}