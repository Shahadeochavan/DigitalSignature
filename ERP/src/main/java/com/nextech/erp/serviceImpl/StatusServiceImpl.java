package com.nextech.erp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.StatusDao;
import com.nextech.erp.factory.StatusRequestResponseFactory;
import com.nextech.erp.model.Status;
import com.nextech.erp.newDTO.StatusDTO;
import com.nextech.erp.service.StatusService;
@Service
public class StatusServiceImpl extends CRUDServiceImpl<Status> implements StatusService {

	@Autowired
	StatusDao statusDao;
	@Override
	public StatusDTO getStatusById(long id) throws Exception {
		
		Status status =  statusDao.getById(Status.class, id);
		if(status==null){
			return null;
		}
		StatusDTO statusDTO = StatusRequestResponseFactory.setStatusDTO(status);
		return statusDTO;
	}
	@Override
	public List<StatusDTO> getStatusList() throws Exception {
		
		List<StatusDTO> statusDTOs = new ArrayList<StatusDTO>();
		List<Status> statusList= statusDao.getList(Status.class);
		if(statusList.isEmpty()){
			return null;
		}
		for (Status status : statusList) {
			StatusDTO statusDTO = StatusRequestResponseFactory.setStatusDTO(status);
			statusDTOs.add(statusDTO);
		}
		return statusDTOs;
	}
	@Override
	public StatusDTO deleteStatus(long id) throws Exception {
		
		Status status =  statusDao.getById(Status.class, id);
		if(status==null){
			return null;
		}
		status.setIsactive(false);
		statusDao.update(status);
		StatusDTO statusDTO = StatusRequestResponseFactory.setStatusDTO(status);
		return statusDTO;
	}
	@Override
	public List<StatusDTO> getStatusByType(String type) throws Exception {
		
		List<StatusDTO> statusDTOs = new ArrayList<StatusDTO>();
		List<Status> statusList= statusDao.getStatusByType(type);
		if(statusList.isEmpty()){
			return null;
		}
		for (Status status : statusList) {
			StatusDTO statusDTO = StatusRequestResponseFactory.setStatusDTO(status);
			statusDTOs.add(statusDTO);
		}
		return statusDTOs;
	}
	
}
