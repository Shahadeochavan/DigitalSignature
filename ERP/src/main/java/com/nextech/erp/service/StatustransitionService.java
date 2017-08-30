package com.nextech.erp.service;

import java.util.List;

import com.nextech.erp.model.Statustransition;
import com.nextech.erp.newDTO.StatusTransitionDTO;

public interface StatustransitionService extends CRUDService<Statustransition>{
	
	public Statustransition getStatustransitionByEmail(String email) throws Exception;
	
	public List<StatusTransitionDTO> getStatusTranstionList() throws Exception;
	
	public StatusTransitionDTO getStatusTranstionbyId(long id) throws Exception;
	
	public StatusTransitionDTO deleteStatusTranstion(long id)throws Exception;
}

