package com.nextech.erp.service;

import java.util.List;

import com.nextech.erp.model.Rmtype;
import com.nextech.erp.newDTO.RMTypeDTO;

public interface RMTypeService extends CRUDService<Rmtype>{
	
	public List<RMTypeDTO>  getRMTypeList() throws Exception;
	
	public RMTypeDTO getRMTypeById(long id) throws Exception;
	
	public RMTypeDTO deleteRMType(long id)throws Exception;

}
