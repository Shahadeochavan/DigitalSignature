package com.nextech.erp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.BOMRMVendorAssociationDao;
import com.nextech.erp.factory.BomRMVendorRequestResponseFactory;
import com.nextech.erp.model.Bomrmvendorassociation;
import com.nextech.erp.newDTO.BomRMVendorAssociationsDTO;
import com.nextech.erp.service.BOMRMVendorAssociationService;
@Service
public class BOMRMVendorAssociationServiceImpl extends CRUDServiceImpl<Bomrmvendorassociation> implements BOMRMVendorAssociationService {

	@Autowired
	BOMRMVendorAssociationDao bOMRMVendorAssociationDao;
	@Override
	public List<BomRMVendorAssociationsDTO> getBomRMVendorByBomId(long bomId)
			throws Exception {
		// TODO Auto-generated method stub
		List<BomRMVendorAssociationsDTO> bomRMVendorAssociationsDTOs = new ArrayList<BomRMVendorAssociationsDTO>();
		List<Bomrmvendorassociation> bomrmvendorassociations = bOMRMVendorAssociationDao.getBomRMVendorByBomId(bomId);
		if(bomrmvendorassociations.isEmpty()){
			return null;
		}
		for (Bomrmvendorassociation bomrmvendorassociation : bomrmvendorassociations) {
			BomRMVendorAssociationsDTO bomRMVendorAssociationsDTO = BomRMVendorRequestResponseFactory.setBOMRMVendorAssoDTO(bomrmvendorassociation);
			bomRMVendorAssociationsDTOs.add(bomRMVendorAssociationsDTO);
		}
		return bomRMVendorAssociationsDTOs;
	}
	


}
