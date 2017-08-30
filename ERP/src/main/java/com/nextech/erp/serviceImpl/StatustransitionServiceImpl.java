package com.nextech.erp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.StatustransitionDao;
import com.nextech.erp.factory.StatusTransitionRequestResponseFactory;
import com.nextech.erp.model.Statustransition;
import com.nextech.erp.newDTO.StatusTransitionDTO;
import com.nextech.erp.service.StatustransitionService;
@Service
public class StatustransitionServiceImpl extends CRUDServiceImpl<Statustransition> implements StatustransitionService {

	@Autowired
	StatustransitionDao statustransitionDao;

	@Override
	public Statustransition getStatustransitionByEmail(String email)
			throws Exception {
		return statustransitionDao.getStatustransitionByEmail(email);
	}

	@Override
	public List<StatusTransitionDTO> getStatusTranstionList() throws Exception {
		// TODO Auto-generated method stub
		List<StatusTransitionDTO> statusTransitionDTOs =  new ArrayList<StatusTransitionDTO>();
		List<Statustransition> statustransitions = statustransitionDao.getList(Statustransition.class);
		if(statustransitions.isEmpty()){
			return null;
		}
		for (Statustransition statustransition : statustransitions) {
			StatusTransitionDTO statusTransitionDTO = StatusTransitionRequestResponseFactory.setStatusTransitinDTO(statustransition);
			statusTransitionDTOs.add(statusTransitionDTO);
		}
		return statusTransitionDTOs;
	}

	@Override
	public StatusTransitionDTO getStatusTranstionbyId(long id) throws Exception {
		// TODO Auto-generated method stub
		Statustransition statustransition = statustransitionDao.getById(Statustransition.class, id);
		if(statustransition==null){
			return null;
		}
		StatusTransitionDTO statusTransitionDTO = StatusTransitionRequestResponseFactory.setStatusTransitinDTO(statustransition);
		return statusTransitionDTO;
	}

	@Override
	public StatusTransitionDTO deleteStatusTranstion(long id) throws Exception {
		// TODO Auto-generated method stub
		Statustransition statustransition = statustransitionDao.getById(Statustransition.class, id);
		if(statustransition==null){
			return null;
		}
		statustransition.setIsactive(false);
		statustransitionDao.update(statustransition);
		StatusTransitionDTO statusTransitionDTO = StatusTransitionRequestResponseFactory.setStatusTransitinDTO(statustransition);
		return statusTransitionDTO;
		
	}

}
