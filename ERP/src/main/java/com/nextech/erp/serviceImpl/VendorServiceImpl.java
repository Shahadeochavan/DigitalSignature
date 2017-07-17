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
	public Vendor getVendorByName(String vendorName) throws Exception {
		// TODO Auto-generated method stub
		return vendorDao.getVendorByName(vendorName);
	}

	@Override
	public List<VendorDTO> getVendorList(List<VendorDTO> vendorDTOs)
			throws Exception {
		// TODO Auto-generated method stub
		vendorDTOs = new ArrayList<VendorDTO>();
		List<Vendor> vendors = vendorDao.getList(Vendor.class);
		for (Vendor vendor : vendors) {
		VendorDTO vendorDTO =	VendorFactory.setVendorList(vendor);
			vendorDTOs.add(vendorDTO);	
		}
		return vendorDTOs;
	}

	@Override
	public VendorDTO getVendorById(long id) throws Exception {
		// TODO Auto-generated method stub
		Vendor vendor = vendorDao.getById(Vendor.class, id);
		VendorDTO vendorDTO = VendorFactory.setVendorList(vendor);
		return vendorDTO;
	}

	@Override
	public void deleteVendor(long id) throws Exception {
		// TODO Auto-generated method stub
		Vendor vendor = vendorDao.getById(Vendor.class, id);
		vendor.setIsactive(false);
		vendorDao.update(vendor);
	}

}
