package com.nextech.erp.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.RmorderinvoiceintakquantityDao;
import com.nextech.erp.model.Rmorderinvoiceintakquantity;
import com.nextech.erp.service.RmorderinvoiceintakquantityService;
@Service
public class RmorderinvoiceintakquantityServiceImpl extends CRUDServiceImpl<Rmorderinvoiceintakquantity> implements
		RmorderinvoiceintakquantityService {
	@Autowired
	RmorderinvoiceintakquantityDao rmorderinvoiceintakquantityDao;

	@Override
	public List<Rmorderinvoiceintakquantity> getRmorderinvoiceintakquantityByRMOInvoiceId(
			long id) throws Exception {
		return rmorderinvoiceintakquantityDao.getRmorderinvoiceintakquantityByRMOInvoiceId(id);
	}

}