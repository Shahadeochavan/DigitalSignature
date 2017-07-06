package com.nextech.erp.daoImpl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nextech.erp.dao.VendorDao;
import com.nextech.erp.model.Vendor;

@Repository
@Transactional
public class VendorDaoImpl extends SuperDaoImpl<Vendor> implements VendorDao {
	@Autowired
	SessionFactory sessionFactory;
	Session session = null;
	Transaction tx = null;

	@Override
	public Vendor getVendorByCompanyName(String companyName) throws Exception {
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Vendor.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("companyName", companyName));
		Vendor vendor = criteria.list().size() > 0 ? (Vendor) criteria.list().get(0) : null;
		 // //session.close();
		return vendor;
	}

	@Override
	public Vendor getVendorByEmail(String email) throws Exception {
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Vendor.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("email", email));
		Vendor vendor = criteria.list().size() > 0 ? (Vendor) criteria.list().get(0) : null;
		 // //session.close();
		return vendor;
	}

	@Override
	public Vendor getVendorByName(String vendorName) throws Exception {
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Vendor.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("firstName", vendorName));
		Vendor vendor = criteria.list().size() > 0 ? (Vendor) criteria.list().get(0) : null;
		 // //session.close();
		return vendor;
	}
}
