package com.nextech.erp.service;

import java.util.List;

import com.nextech.erp.model.Rawmaterial;
import com.nextech.erp.newDTO.RMVendorAssociationDTO;
import com.nextech.erp.newDTO.RawMaterialDTO;

public interface RawmaterialService extends CRUDService<Rawmaterial>{
	
	List<RawMaterialDTO> getRawMaterialByRMOrderId(Long id) throws Exception;
	
	List<RMVendorAssociationDTO> getRawmaterialByVenodrId(long id) throws Exception;
	
	public Rawmaterial getRMByRMId(long id) throws Exception;
	
	public List<RawMaterialDTO>  getRMList() throws Exception;
	
	public RawMaterialDTO  getRMDTO(long id) throws Exception;
	
	public void deleteRM(long id) throws Exception;
	
	List<RawMaterialDTO> getRMByRMTypeId(long id) throws Exception;
	
}