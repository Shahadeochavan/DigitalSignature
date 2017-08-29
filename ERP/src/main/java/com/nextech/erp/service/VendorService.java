package com.nextech.erp.service;

import java.util.List;

import com.nextech.erp.model.Vendor;
import com.nextech.erp.newDTO.VendorDTO;

public interface VendorService extends CRUDService<Vendor>{

	public Vendor getVendorByCompanyName(String companyName) throws Exception;

	public Vendor getVendorByEmail(String email) throws Exception;

	public Vendor getVendorByName(String vendorName) throws Exception;
	
	public List<VendorDTO>  getVendorList(List<VendorDTO> vendorDTOs) throws Exception;
	
	public VendorDTO getVendorById(long id) throws Exception;
	
	public VendorDTO deleteVendor(long id) throws Exception;
}
