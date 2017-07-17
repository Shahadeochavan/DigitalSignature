package com.nextech.erp.service;

import java.util.List;

import com.nextech.erp.model.Usertype;
import com.nextech.erp.newDTO.UserTypeDTO;

public interface UserTypeService extends CRUDService<Usertype>{
	
	public UserTypeDTO getUserTypeDto(long id)throws Exception;
	 
	public List<UserTypeDTO> getUserTypeDTO() throws Exception;
	
	public void deleteUserType(long id) throws Exception;

}
