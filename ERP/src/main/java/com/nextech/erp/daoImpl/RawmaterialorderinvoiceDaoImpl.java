package com.nextech.erp.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nextech.erp.dao.RawmaterialorderinvoiceDao;
import com.nextech.erp.model.Rawmaterialorderinvoice;

@Repository
@Transactional
public class RawmaterialorderinvoiceDaoImpl extends
		SuperDaoImpl<Rawmaterialorderinvoice> implements
		RawmaterialorderinvoiceDao {
	@Autowired
	SessionFactory sessionFactory;
	Session session = null;
	Transaction tx = null;

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<Rawmaterialorderinvoice> getRawmaterialorderinvoiceByStatusId(
			long id) throws Exception {
		session = sessionFactory.openSession();
		Criteria criteria = session
				.createCriteria(Rawmaterialorderinvoice.class);
		criteria.add(Restrictions.eq("status.id", id));
		return criteria.list();
	}
	@Override
	public Rawmaterialorderinvoice getRMOrderInvoiceByInVoiceNoVendorNameAndPoNo(String invoiceNo,String vendorName,int poNO) throws Exception {
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Rawmaterialorderinvoice.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("invoice_No", invoiceNo));
		criteria.add(Restrictions.eq("vendorname", vendorName));
		criteria.add(Restrictions.eq("po_No", poNO));
		Rawmaterialorderinvoice rawmaterialorderinvoice = criteria.list().size() > 0 ? (Rawmaterialorderinvoice) criteria.list().get(0)
				: null;
		//  //session.close();
		return rawmaterialorderinvoice;
	}
}
