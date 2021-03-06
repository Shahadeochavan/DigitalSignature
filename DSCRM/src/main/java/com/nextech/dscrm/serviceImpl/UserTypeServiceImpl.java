package com.nextech.dscrm.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.dscrm.dao.UserTypeDao;
import com.nextech.dscrm.factory.UserTypeFactory;
import com.nextech.dscrm.model.Usertype;
import com.nextech.dscrm.newDTO.UserTypeDTO;
import com.nextech.dscrm.service.UserTypeService;
@Service
public class UserTypeServiceImpl extends CRUDServiceImpl<Usertype> implements UserTypeService {

	@Autowired
	UserTypeDao userTypeDao;
	@Override
	public UserTypeDTO getUserTypeDto(long id) throws Exception {
		
		Usertype usertype = userTypeDao.getById(Usertype.class, id);
		if(usertype==null){
			return null;
		}
		UserTypeDTO userTypeDTO = UserTypeFactory.setUserTypeDto(usertype);
		return userTypeDTO;
	}
	@Override
	public List<UserTypeDTO> getUserTypeDTO() throws Exception {
		
		List<UserTypeDTO> userTypeDTOs = new ArrayList<UserTypeDTO>();
		List<Usertype> usertypes = userTypeDao.getList(Usertype.class);
		if(usertypes.isEmpty()){
			return null;
		}
		for (Usertype usertype : usertypes) {
			UserTypeDTO userTypeDTO =  UserTypeFactory.setUserTypeDto(usertype);
			userTypeDTOs.add(userTypeDTO);
		}
		return userTypeDTOs;
	}
	@Override
	public UserTypeDTO deleteUserType(long id) throws Exception {
		
		Usertype usertype = userTypeDao.getById(Usertype.class, id);
		if(usertype==null){
			return null;
		}
		usertype.setIsactive(false);
		userTypeDao.update(usertype);
		UserTypeDTO userTypeDTO = UserTypeFactory.setUserTypeDto(usertype);
		return userTypeDTO;
		
	}
	@Override
	public Usertype getUserTypeByUserTypeName(String userTypeName)
			throws Exception {
		// TODO Auto-generated method stub
		return userTypeDao.getUserTypeByUserTypeName(userTypeName);
	}

}
