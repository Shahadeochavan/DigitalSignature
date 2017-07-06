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

import com.nextech.erp.dao.RmorderinvoiceintakquantityDao;
import com.nextech.erp.model.Rmorderinvoiceintakquantity;

@Repository
@Transactional
public class RmorderinvoiceintakquantityDaoImpl extends
		SuperDaoImpl<Rmorderinvoiceintakquantity> implements
		RmorderinvoiceintakquantityDao {
	@Autowired
	SessionFactory sessionFactory;
	Session session = null;
	Transaction tx = null;

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<Rmorderinvoiceintakquantity> getRmorderinvoiceintakquantityByRMOInvoiceId(long id) throws Exception {
		session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Rmorderinvoiceintakquantity.class);
		criteria.add(Restrictions.eq("rawmaterialorderinvoice.id", id));
		return criteria.list();
	}

}
