package com.nextech.erp.service;


import java.util.List;

import com.nextech.erp.model.Status;
import com.nextech.erp.newDTO.StatusDTO;

public interface StatusService extends CRUDService<Status>{
	
	public StatusDTO  getStatusById(long id) throws Exception;
	
	public List<StatusDTO> getStatusList() throws Exception;
	
	public void deleteStatus(long id) throws Exception;
	
}


