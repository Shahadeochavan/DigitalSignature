package com.nextech.erp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.RawmaterialorderassociationDao;
import com.nextech.erp.factory.RMOrderAssociationRequestResponseFactory;
import com.nextech.erp.model.Rawmaterialorderassociation;
import com.nextech.erp.newDTO.RMOrderAssociationDTO;
import com.nextech.erp.service.RawmaterialorderassociationService;
@Service
public class RawmaterialorderassociationServiceImpl extends CRUDServiceImpl<Rawmaterialorderassociation> implements RawmaterialorderassociationService {

	@Autowired
	RawmaterialorderassociationDao rawmaterialorderassociationDao;

	@Override
	public List<RMOrderAssociationDTO> getRMOrderRMAssociationByRMOrderId(long id) throws Exception {
		
		List<RMOrderAssociationDTO> rmOrderAssociationDTOs = new ArrayList<RMOrderAssociationDTO>();
		List<Rawmaterialorderassociation> rawmaterialorderassociations = rawmaterialorderassociationDao.getRMOrderRMAssociationByRMOrderId(id);
		for (Rawmaterialorderassociation rawmaterialorderassociation : rawmaterialorderassociations) {
			RMOrderAssociationDTO rmOrderAssociationDTO = RMOrderAssociationRequestResponseFactory.setRMOrderDTO(rawmaterialorderassociation);
			rmOrderAssociationDTOs.add(rmOrderAssociationDTO);
		}
		return rmOrderAssociationDTOs;
	}

	@Override
	public Rawmaterialorderassociation getRMOrderRMAssociationByRMOrderIdandRMId(long id, long rmId) throws Exception {
		
		return rawmaterialorderassociationDao.getRMOrderRMAssociationByRMOrderIdandRMId(id, rmId);
	}

	@Override
	public RMOrderAssociationDTO getRMOrderAssoById(long id) throws Exception {
		
		Rawmaterialorderassociation rawmaterialorderassociation = rawmaterialorderassociationDao.getById(Rawmaterialorderassociation.class, id);
		RMOrderAssociationDTO rmOrderAssociationDTO = RMOrderAssociationRequestResponseFactory.setRMOrderDTO(rawmaterialorderassociation);
		return rmOrderAssociationDTO;
	}

	@Override
	public List<RMOrderAssociationDTO> getRMOrderAssoList() throws Exception {
		
		List<RMOrderAssociationDTO> rmOrderAssociationDTOs = new ArrayList<RMOrderAssociationDTO>();
		List<Rawmaterialorderassociation> rawmaterialorderassociations = rawmaterialorderassociationDao.getList(Rawmaterialorderassociation.class);
		for (Rawmaterialorderassociation rawmaterialorderassociation : rawmaterialorderassociations) {
			RMOrderAssociationDTO rmOrderAssociationDTO = RMOrderAssociationRequestResponseFactory.setRMOrderDTO(rawmaterialorderassociation);
			rmOrderAssociationDTOs.add(rmOrderAssociationDTO);
		}
		return rmOrderAssociationDTOs;
	}

	@Override
	public RMOrderAssociationDTO deleteRMOrderAsso(long id) throws Exception {
		
		Rawmaterialorderassociation rawmaterialorderassociation = rawmaterialorderassociationDao.getById(Rawmaterialorderassociation.class, id);
		if(rawmaterialorderassociation==null){
			return null;
		}
		rawmaterialorderassociation.setIsactive(false);
		rawmaterialorderassociationDao.update(rawmaterialorderassociation);
		RMOrderAssociationDTO rmOrderAssociationDTO = RMOrderAssociationRequestResponseFactory.setRMOrderDTO(rawmaterialorderassociation);
		return rmOrderAssociationDTO;
	}

}
