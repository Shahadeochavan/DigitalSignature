package com.nextech.erp.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.nextech.erp.dto.StoreOutDTO;
import com.nextech.erp.model.Storeout;
import com.nextech.erp.status.UserStatus;

public interface StoreoutService extends CRUDService<Storeout>{
	
	public UserStatus  createStoreOut(StoreOutDTO  storeOutDTO,HttpServletRequest request) throws Exception;
	
	public List<StoreOutDTO> getStoreOutlist() throws Exception;
	
	public StoreOutDTO getStoreOutById(long id) throws Exception;
	
	public void deleteStoreOutById(long id) throws Exception;

}
