package com.nextech.erp.dao;

import java.util.List;

import com.nextech.erp.model.Rmorderinvoiceintakquantity;

public interface RmorderinvoiceintakquantityDao extends
		SuperDao<Rmorderinvoiceintakquantity> {
	public List<Rmorderinvoiceintakquantity> getRmorderinvoiceintakquantityByRMOInvoiceId(
			long id) throws Exception;

}