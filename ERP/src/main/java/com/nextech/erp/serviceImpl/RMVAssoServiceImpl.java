package com.nextech.erp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.RMVAssoDao;
import com.nextech.erp.factory.RMVendorAssoRequestResponseFactory;
import com.nextech.erp.model.Rawmaterialvendorassociation;
import com.nextech.erp.newDTO.RMVendorAssociationDTO;
import com.nextech.erp.service.RMVAssoService;
@Service
public class RMVAssoServiceImpl extends CRUDServiceImpl<Rawmaterialvendorassociation> implements RMVAssoService{

	@Autowired
	RMVAssoDao rMVAssoDao;
	
	@Override
	public Rawmaterialvendorassociation getRMVAssoByVendorIdRMId(
			long vendorId, long rmId) throws Exception {
		
		return rMVAssoDao.getRMVAssoByVendorIdRMId(vendorId, rmId);
	}

	@Override
	public List<RMVendorAssociationDTO> getRawmaterialvendorassociationListByRMId(long id) throws Exception {
		
		List<RMVendorAssociationDTO> rmVendorAssociationDTOs = new ArrayList<RMVendorAssociationDTO>();
		List<Rawmaterialvendorassociation> rawmaterialvendorassociations = rMVAssoDao.getRawmaterialvendorassociationListByRMId(id);
		if(rawmaterialvendorassociations.isEmpty()){
			return null;
		}
		for (Rawmaterialvendorassociation rawmaterialvendorassociation : rawmaterialvendorassociations) {
			RMVendorAssociationDTO rmVendorAssociationDTO = RMVendorAssoRequestResponseFactory.setRMVendorList(rawmaterialvendorassociation);
			rmVendorAssociationDTOs.add(rmVendorAssociationDTO);
		}
		return rmVendorAssociationDTOs;
	}

	@Override
	public RMVendorAssociationDTO getRMVAssoByRMId(long rmId)
			throws Exception {
		
		Rawmaterialvendorassociation rawmaterialvendorassociation = rMVAssoDao.getRMVAssoByRMId(rmId);
		if(rawmaterialvendorassociation==null){
			return null;
		}
		RMVendorAssociationDTO rmVendorAssociationDTO = RMVendorAssoRequestResponseFactory.setRMVendorList(rawmaterialvendorassociation);
		return rmVendorAssociationDTO;
	}

	@Override
	public List<RMVendorAssociationDTO> getRMVendorList() throws Exception {
		
		List<RMVendorAssociationDTO> rmVendorAssociationDTOs =  new ArrayList<RMVendorAssociationDTO>();
		List<Rawmaterialvendorassociation> rawmaterialvendorassociations = rMVAssoDao.getList(Rawmaterialvendorassociation.class);
		if(rawmaterialvendorassociations.isEmpty()){
			return null;
		}
		for (Rawmaterialvendorassociation rawmaterialvendorassociation : rawmaterialvendorassociations) {
			RMVendorAssociationDTO rmVendorAssociationDTO = RMVendorAssoRequestResponseFactory.setRMVendorList(rawmaterialvendorassociation);
			rmVendorAssociationDTOs.add(rmVendorAssociationDTO);
			
		}
		return rmVendorAssociationDTOs;
	}

	@Override
	public RMVendorAssociationDTO getRMVendor(long id) throws Exception {
		
		Rawmaterialvendorassociation rawmaterialvendorassociation = rMVAssoDao.getById(Rawmaterialvendorassociation.class, id);
		if(rawmaterialvendorassociation==null){
			return null;
		}
		RMVendorAssociationDTO rmVendorAssociationDTO = RMVendorAssoRequestResponseFactory.setRMVendorList(rawmaterialvendorassociation);
		return rmVendorAssociationDTO;
	}

	@Override
	public RMVendorAssociationDTO deleteRMVendor(long id) throws Exception {
		
		Rawmaterialvendorassociation rawmaterialvendorassociation = rMVAssoDao.getById(Rawmaterialvendorassociation.class, id);
		if(rawmaterialvendorassociation==null){
			return null;
		}
		rawmaterialvendorassociation.setIsactive(false);
		rMVAssoDao.update(rawmaterialvendorassociation);
         RMVendorAssociationDTO rmVendorAssociationDTO = RMVendorAssoRequestResponseFactory.setRMVendorList(rawmaterialvendorassociation);
		return rmVendorAssociationDTO;
		
	}
	
}

