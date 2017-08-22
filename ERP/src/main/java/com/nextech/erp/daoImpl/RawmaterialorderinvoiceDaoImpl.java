package com.nextech.erp.daoImpl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.nextech.erp.dao.RawmaterialorderinvoiceDao;
import com.nextech.erp.model.Rawmaterialorderinvoice;

@Repository

public class RawmaterialorderinvoiceDaoImpl extends
		SuperDaoImpl<Rawmaterialorderinvoice> implements
		RawmaterialorderinvoiceDao {
	@Autowired
	SessionFactory sessionFactory;
	Session session = null;
	Transaction tx = null;

	@Override
	public List<Rawmaterialorderinvoice> getRawmaterialorderinvoiceByStatusId(
			long id) throws Exception {
	
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Rawmaterialorderinvoice> criteria = builder.createQuery(Rawmaterialorderinvoice.class);
		Root<Rawmaterialorderinvoice> userRoot  = (Root<Rawmaterialorderinvoice>) criteria.from(Rawmaterialorderinvoice.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("status"), id));
		TypedQuery<Rawmaterialorderinvoice> query = session.createQuery(criteria);
		return query.getResultList();
	}
	@Override
	public Rawmaterialorderinvoice getRMOrderInvoiceByInVoiceNoVendorNameAndPoNo(String invoiceNo,String vendorName,int poNO) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Rawmaterialorderinvoice> criteria = builder.createQuery(Rawmaterialorderinvoice.class);
		Root<Rawmaterialorderinvoice> userRoot = criteria.from(Rawmaterialorderinvoice.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("invoice_No"), invoiceNo),builder.equal(userRoot.get("vendorname"), vendorName),builder.equal(userRoot.get("po_No"), poNO),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Rawmaterialorderinvoice> query = session.createQuery(criteria);
		List<Rawmaterialorderinvoice> results = query.getResultList();
		  if (results.isEmpty()) {
		        return null;
		    }
		    return results.get(0);
	}
}
