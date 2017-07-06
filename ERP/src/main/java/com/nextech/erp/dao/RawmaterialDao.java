package com.nextech.erp.dao;

import java.util.List;

import com.nextech.erp.model.Rawmaterial;
import com.nextech.erp.model.Rawmaterialvendorassociation;

public interface RawmaterialDao extends SuperDao<Rawmaterial>{
	
	List<Rawmaterialvendorassociation> getRawmaterialByVenodrId(long id) throws Exception;
	
	public Rawmaterial getRMByRMId(long id) throws Exception ;

}
