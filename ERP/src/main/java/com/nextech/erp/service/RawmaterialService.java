package com.nextech.erp.service;

import java.util.List;

import com.nextech.erp.model.Rawmaterial;
import com.nextech.erp.model.Rawmaterialvendorassociation;

public interface RawmaterialService extends CRUDService<Rawmaterial>{
	
	List<Rawmaterial> getRawMaterialByRMOrderId(Long id) throws Exception;
	
	List<Rawmaterialvendorassociation> getRawmaterialByVenodrId(long id) throws Exception;
	
	public Rawmaterial getRMByRMId(long id) throws Exception;
}