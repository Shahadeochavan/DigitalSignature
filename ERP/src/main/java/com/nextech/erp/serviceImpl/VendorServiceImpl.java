package com.nextech.erp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.VendorDao;
import com.nextech.erp.factory.VendorFactory;
import com.nextech.erp.model.Vendor;
import com.nextech.erp.newDTO.VendorDTO;
import com.nextech.erp.service.VendorService;
@Service
public class VendorServiceImpl extends CRUDServiceImpl<Vendor> implements VendorService {

	@Autowired
	VendorDao vendorDao;

	@Override
	public Vendor getVendorByCompanyName(String companyName) throws Exception {
		return vendorDao.getVendorByCompanyName(companyName);
	}

	@Override
	public Vendor getVendorByEmail(String email) throws Exception {
		return vendorDao.getVendorByEmail(email);
	}


	@Override
	public List<VendorDTO> getVendorList(List<VendorDTO> vendorDTOs)
			throws Exception {
		
		vendorDTOs = new ArrayList<VendorDTO>();
		List<Vendor> vendors = vendorDao.getList(Vendor.class);
		if(vendors.isEmpty()){
			return null;
		}
		for (Vendor vendor : vendors) {
		VendorDTO vendorDTO =	VendorFactory.setVendorList(vendor);
			vendorDTOs.add(vendorDTO);	
		}
		return vendorDTOs;
	}

	@Override
	public VendorDTO getVendorById(long id) throws Exception {
		
		Vendor vendor = vendorDao.getById(Vendor.class, id);
		if(vendor==null){
			return null;
		}
		VendorDTO vendorDTO = VendorFactory.setVendorList(vendor);
		return vendorDTO;
	}

	@Override
	public VendorDTO deleteVendor(long id) throws Exception {
		
		Vendor vendor = vendorDao.getById(Vendor.class, id);
		if(vendor==null){
			return null;
		}
		vendor.setIsactive(false);
		vendorDao.update(vendor);
		VendorDTO vendorDTO = VendorFactory.setVendorList(vendor);
		return vendorDTO;
	}

}
