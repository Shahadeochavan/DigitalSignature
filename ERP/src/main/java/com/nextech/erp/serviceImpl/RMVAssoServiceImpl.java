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
		// TODO Auto-generated method stub
		return rMVAssoDao.getRMVAssoByVendorIdRMId(vendorId, rmId);
	}

	@Override
	public List<RMVendorAssociationDTO> getRawmaterialvendorassociationListByRMId(long id) throws Exception {
		// TODO Auto-generated method stub
		List<RMVendorAssociationDTO> rmVendorAssociationDTOs = new ArrayList<RMVendorAssociationDTO>();
		List<Rawmaterialvendorassociation> rawmaterialvendorassociations = rMVAssoDao.getRawmaterialvendorassociationListByRMId(id);
		for (Rawmaterialvendorassociation rawmaterialvendorassociation : rawmaterialvendorassociations) {
			RMVendorAssociationDTO rmVendorAssociationDTO = RMVendorAssoRequestResponseFactory.setRMVendorList(rawmaterialvendorassociation);
			rmVendorAssociationDTOs.add(rmVendorAssociationDTO);
		}
		return rmVendorAssociationDTOs;
	}

	@Override
	public Rawmaterialvendorassociation getRMVAssoByRMId(long rmId)
			throws Exception {
		// TODO Auto-generated method stub
		return rMVAssoDao.getRMVAssoByRMId(rmId);
	}

	@Override
	public List<RMVendorAssociationDTO> getRMVendorList() throws Exception {
		// TODO Auto-generated method stub
		List<RMVendorAssociationDTO> rmVendorAssociationDTOs =  new ArrayList<RMVendorAssociationDTO>();
		List<Rawmaterialvendorassociation> rawmaterialvendorassociations = rMVAssoDao.getList(Rawmaterialvendorassociation.class);
		for (Rawmaterialvendorassociation rawmaterialvendorassociation : rawmaterialvendorassociations) {
			RMVendorAssociationDTO rmVendorAssociationDTO = RMVendorAssoRequestResponseFactory.setRMVendorList(rawmaterialvendorassociation);
			rmVendorAssociationDTOs.add(rmVendorAssociationDTO);
			
		}
		return rmVendorAssociationDTOs;
	}

	@Override
	public RMVendorAssociationDTO getRMVendor(long id) throws Exception {
		// TODO Auto-generated method stub
		Rawmaterialvendorassociation rawmaterialvendorassociation = rMVAssoDao.getById(Rawmaterialvendorassociation.class, id);
		RMVendorAssociationDTO rmVendorAssociationDTO = RMVendorAssoRequestResponseFactory.setRMVendorList(rawmaterialvendorassociation);
		
		return rmVendorAssociationDTO;
	}

	@Override
	public void deleteRMVendor(long id) throws Exception {
		// TODO Auto-generated method stub
		Rawmaterialvendorassociation rawmaterialvendorassociation = rMVAssoDao.getById(Rawmaterialvendorassociation.class, id);
		rawmaterialvendorassociation.setIsactive(false);
		rMVAssoDao.update(rawmaterialvendorassociation);
		
	}
	
}

