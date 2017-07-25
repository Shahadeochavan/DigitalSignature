package com.nextech.erp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.RawmaterialDao;
import com.nextech.erp.dao.RawmaterialorderassociationDao;
import com.nextech.erp.factory.RMRequestResponseFactory;
import com.nextech.erp.factory.RMVendorAssoRequestResponseFactory;
import com.nextech.erp.model.Rawmaterial;
import com.nextech.erp.model.Rawmaterialorderassociation;
import com.nextech.erp.model.Rawmaterialvendorassociation;
import com.nextech.erp.newDTO.RMVendorAssociationDTO;
import com.nextech.erp.newDTO.RawMaterialDTO;
import com.nextech.erp.service.RawmaterialService;
@Service
public class RawmaterialServiceImpl extends CRUDServiceImpl<Rawmaterial> implements RawmaterialService {
	
	@Autowired
	RawmaterialDao rawmaterialDao;

	@Autowired
	RawmaterialorderassociationDao rawmaterialorderassociationDao;
	
	@Override
	public List<RawMaterialDTO> getRawMaterialByRMOrderId(Long id) throws Exception {
		List<RawMaterialDTO> rawmaterials = new ArrayList<RawMaterialDTO>();
		List<Rawmaterialorderassociation> rawmaterialorderassociations = rawmaterialorderassociationDao.getRMOrderRMAssociationByRMOrderId(id);
		if(rawmaterialorderassociations != null && !rawmaterialorderassociations.isEmpty() && rawmaterialorderassociations.size()>0){
			for (Rawmaterialorderassociation rawmaterialorderassociation : rawmaterialorderassociations) {
				Rawmaterial rawmaterial = rawmaterialDao.getById(Rawmaterial.class, rawmaterialorderassociation.getRawmaterial().getId());
				RawMaterialDTO rawMaterialDTO = RMRequestResponseFactory.setRawMaterialDTO(rawmaterial);
				rawmaterials.add(rawMaterialDTO);
			
			}
		}
		return rawmaterials;
	}

	@Override
	public List<RMVendorAssociationDTO> getRawmaterialByVenodrId(long id)
			throws Exception {
		// TODO Auto-generated method stub
		List<RMVendorAssociationDTO> rmVendorAssociationDTOs = new ArrayList<RMVendorAssociationDTO>();
		List<Rawmaterialvendorassociation> rawmaterialvendorassociations = rawmaterialDao.getRawmaterialByVenodrId(id);
		for (Rawmaterialvendorassociation rawmaterialvendorassociation : rawmaterialvendorassociations) {
			RMVendorAssociationDTO rmVendorAssociationDTO = RMVendorAssoRequestResponseFactory.setRMVendorList(rawmaterialvendorassociation);
			rmVendorAssociationDTOs.add(rmVendorAssociationDTO);
		}
		
		return rmVendorAssociationDTOs;
	}

	@Override
	public Rawmaterial getRMByRMId(long id) throws Exception {
		// TODO Auto-generated method stub
		return rawmaterialDao.getRMByRMId(id);
	}

	@Override
	public List<RawMaterialDTO> getRMList() throws Exception {
		// TODO Auto-generated method stub
		List<RawMaterialDTO> rawMaterialDTOs = new ArrayList<RawMaterialDTO>();
		List<Rawmaterial> rawmaterials = rawmaterialDao.getList(Rawmaterial.class);
		for (Rawmaterial rawmaterial : rawmaterials) {
			RawMaterialDTO rawMaterialDTO = RMRequestResponseFactory.setRawMaterialDTO(rawmaterial);
			rawMaterialDTOs.add(rawMaterialDTO);
		}
		return rawMaterialDTOs; 
	}

	@Override
	public RawMaterialDTO getRMDTO(long id) throws Exception {
		// TODO Auto-generated method stub
		Rawmaterial rawmaterial = rawmaterialDao.getById(Rawmaterial.class, id);
		RawMaterialDTO rawMaterialDTO = RMRequestResponseFactory.setRawMaterialDTO(rawmaterial);
		return rawMaterialDTO;
	}

	@Override
	public void deleteRM(long id) throws Exception {
		// TODO Auto-generated method stub
		Rawmaterial rawmaterial = rawmaterialDao.getById(Rawmaterial.class, id);
		rawmaterial.setIsactive(false);
		rawmaterialDao.update(rawmaterial);
		
	}

	@Override
	public List<RawMaterialDTO> getRMByRMTypeId(long id) throws Exception {
		// TODO Auto-generated method stub
		List<RawMaterialDTO> rawMaterialDTOs =  new ArrayList<RawMaterialDTO>();
		List<Rawmaterial> rawmaterials = rawmaterialDao.getRMByRMTypeId(id);
		for (Rawmaterial rawmaterial : rawmaterials) {
			RawMaterialDTO rawMaterialDTO = RMRequestResponseFactory.setRawMaterialDTO(rawmaterial);
			rawMaterialDTOs.add(rawMaterialDTO);
		}
		return rawMaterialDTOs;
	}
}
