package com.nextech.erp.service;

import java.util.List;

import com.nextech.erp.model.Rmorderinvoiceintakquantity;

public interface RmorderinvoiceintakquantityService extends
		CRUDService<Rmorderinvoiceintakquantity> {
	public List<Rmorderinvoiceintakquantity> getRmorderinvoiceintakquantityByRMOInvoiceId(
			long id) throws Exception;
}