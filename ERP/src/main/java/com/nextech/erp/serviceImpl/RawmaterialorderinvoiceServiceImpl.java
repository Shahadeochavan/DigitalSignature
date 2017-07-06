package com.nextech.erp.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nextech.erp.dao.RawmaterialorderinvoiceDao;
import com.nextech.erp.model.Rawmaterialorderinvoice;
import com.nextech.erp.service.RawmaterialorderinvoiceService;
@Service
public class RawmaterialorderinvoiceServiceImpl extends CRUDServiceImpl<Rawmaterialorderinvoice> implements RawmaterialorderinvoiceService {
	
	@Autowired
	RawmaterialorderinvoiceDao rawmaterialorderinvoiceDao;

	@Override
	public List<Rawmaterialorderinvoice> getRawmaterialorderinvoiceByStatusId(long id) throws Exception {
		return rawmaterialorderinvoiceDao.getRawmaterialorderinvoiceByStatusId(id);
	}

	@Override
	public Rawmaterialorderinvoice getRMOrderInvoiceByInVoiceNoVendorNameAndPoNo(String invoiceNo,String vendorName,int poNo) throws Exception {
		return rawmaterialorderinvoiceDao.getRMOrderInvoiceByInVoiceNoVendorNameAndPoNo(invoiceNo,vendorName,poNo);
	}
}
