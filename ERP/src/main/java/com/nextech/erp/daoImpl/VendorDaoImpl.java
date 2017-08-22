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
import com.nextech.erp.dao.VendorDao;
import com.nextech.erp.model.Vendor;

@Repository

public class VendorDaoImpl extends SuperDaoImpl<Vendor> implements VendorDao {
	@Autowired
	SessionFactory sessionFactory;
	Session session = null;
	Transaction tx = null;

	@Override
	public Vendor getVendorByCompanyName(String companyName) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Vendor> criteria = builder.createQuery(Vendor.class);
		Root<Vendor> userRoot = criteria.from(Vendor.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("companyName"), companyName),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Vendor> query = session.createQuery(criteria);
		List<Vendor> results = query.getResultList();
		  if (results.isEmpty()) {
		        return null;
		    }
		    return results.get(0);
	}

	@Override
	public Vendor getVendorByEmail(String email) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Vendor> criteria = builder.createQuery(Vendor.class);
		Root<Vendor> userRoot = criteria.from(Vendor.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("email"), email),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Vendor> query = session.createQuery(criteria);
		List<Vendor> results = query.getResultList();
		  if (results.isEmpty()) {
		        return null;
		    }
		    return results.get(0);
	}

	@Override
	public Vendor getVendorByName(String vendorName) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Vendor> criteria = builder.createQuery(Vendor.class);
		Root<Vendor> userRoot = criteria.from(Vendor.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("firstName"), vendorName),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Vendor> query = session.createQuery(criteria);
		List<Vendor> results = query.getResultList();
		  if (results.isEmpty()) {
		        return null;
		    }
		    return results.get(0);
	}
}
