package com.nextech.dscrm.service;

import java.util.List;

import com.nextech.dscrm.model.Usertype;
import com.nextech.dscrm.newDTO.UserTypeDTO;

public interface UserTypeService extends CRUDService<Usertype>{
	
	public UserTypeDTO getUserTypeDto(long id)throws Exception;
	 
	public List<UserTypeDTO> getUserTypeDTO() throws Exception;
	
	public UserTypeDTO deleteUserType(long id) throws Exception;
	
	public Usertype getUserTypeByUserTypeName(String userTypeName) throws Exception;

}
