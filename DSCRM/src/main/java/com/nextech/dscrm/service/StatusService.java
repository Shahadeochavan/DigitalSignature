package com.nextech.dscrm.service;


import java.util.List;

import com.nextech.dscrm.model.Status;
import com.nextech.dscrm.newDTO.StatusDTO;

public interface StatusService extends CRUDService<Status>{
	
	public StatusDTO  getStatusById(long id) throws Exception;
	
	public List<StatusDTO> getStatusList() throws Exception;
	
	public StatusDTO deleteStatus(long id) throws Exception;
	
	public List<StatusDTO> getStatusByType(String type) throws Exception;
	
}


