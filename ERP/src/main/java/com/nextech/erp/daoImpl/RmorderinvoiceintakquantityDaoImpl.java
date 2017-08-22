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

import com.nextech.erp.dao.RmorderinvoiceintakquantityDao;
import com.nextech.erp.model.Rmorderinvoiceintakquantity;

@Repository

public class RmorderinvoiceintakquantityDaoImpl extends
		SuperDaoImpl<Rmorderinvoiceintakquantity> implements
		RmorderinvoiceintakquantityDao {
	@Autowired
	SessionFactory sessionFactory;
	Session session = null;
	Transaction tx = null;

	@Override
	public List<Rmorderinvoiceintakquantity> getRmorderinvoiceintakquantityByRMOInvoiceId(long id) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Rmorderinvoiceintakquantity> criteria = builder.createQuery(Rmorderinvoiceintakquantity.class);
		Root<Rmorderinvoiceintakquantity> userRoot  = (Root<Rmorderinvoiceintakquantity>) criteria.from(Rmorderinvoiceintakquantity.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("rawmaterialorderinvoice"), id),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Rmorderinvoiceintakquantity> query = session.createQuery(criteria);
		return query.getResultList();
	}

}
