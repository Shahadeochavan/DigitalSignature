package com.nextech.erp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.RawmaterialDao;
import com.nextech.erp.dao.RawmaterialorderassociationDao;
import com.nextech.erp.model.Rawmaterial;
import com.nextech.erp.model.Rawmaterialorderassociation;
import com.nextech.erp.model.Rawmaterialvendorassociation;
import com.nextech.erp.service.RawmaterialService;
@Service
public class RawmaterialServiceImpl extends CRUDServiceImpl<Rawmaterial> implements RawmaterialService {
	
	@Autowired
	RawmaterialDao rawmaterialDao;

	@Autowired
	RawmaterialorderassociationDao rawmaterialorderassociationDao;
	
	@Override
	public List<Rawmaterial> getRawMaterialByRMOrderId(Long id) throws Exception {
		List<Rawmaterial> rawmaterials = new ArrayList<>();
		List<Rawmaterialorderassociation> rawmaterialorderassociations = rawmaterialorderassociationDao.getRMOrderRMAssociationByRMOrderId(id);
		if(rawmaterialorderassociations != null && !rawmaterialorderassociations.isEmpty() && rawmaterialorderassociations.size()>0){
			for (Rawmaterialorderassociation rawmaterialorderassociation : rawmaterialorderassociations) {
				rawmaterials.add(rawmaterialDao.getById(Rawmaterial.class, rawmaterialorderassociation.getRawmaterial().getId()));
			}
		}
		return rawmaterials;
	}

	@Override
	public List<Rawmaterialvendorassociation> getRawmaterialByVenodrId(long id)
			throws Exception {
		// TODO Auto-generated method stub
		return rawmaterialDao.getRawmaterialByVenodrId(id);
	}

	@Override
	public Rawmaterial getRMByRMId(long id) throws Exception {
		// TODO Auto-generated method stub
		return rawmaterialDao.getRMByRMId(id);
	}
}
