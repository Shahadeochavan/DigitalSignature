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
		
		List<RMTypeDTO> rmTypeDTOs = new ArrayList<RMTypeDTO>();
		List<Rmtype> rmtypes = rMTypeDao.getList(Rmtype.class);
		if(rmtypes.isEmpty()){
			return null;
		}
		for (Rmtype rmtype : rmtypes) {
			RMTypeDTO rmTypeDTO = RMTypeRequestResponseFactory.setRMTypeDTO(rmtype);
			rmTypeDTOs.add(rmTypeDTO);
		}
		return rmTypeDTOs;
	}

	@Override
	public RMTypeDTO getRMTypeById(long id) throws Exception {
		
		Rmtype rmtype  = rMTypeDao.getById(Rmtype.class, id);
		if(rmtype==null){
			return null;
		}
		RMTypeDTO rmTypeDTO = RMTypeRequestResponseFactory.setRMTypeDTO(rmtype);
		return rmTypeDTO;
	}

	@Override
	public RMTypeDTO deleteRMType(long id) throws Exception {
		
		Rmtype rmtype  = rMTypeDao.getById(Rmtype.class, id);
		if(rmtype==null){
			return null;
		}
		rmtype.setIsactive(false);
		rMTypeDao.update(rmtype);
		RMTypeDTO rmTypeDTO = RMTypeRequestResponseFactory.setRMTypeDTO(rmtype);
		return rmTypeDTO;
	}

	@Override
	public Rmtype getRMTypeByRMTypeName(String rmTypeName) throws Exception {
		// TODO Auto-generated method stub
		return rMTypeDao.getRMTypeByRMTypeName(rmTypeName);
	}

}
