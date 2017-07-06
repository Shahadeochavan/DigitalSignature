package com.nextech.erp.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nextech.erp.dao.ProductRMAssoDao;
import com.nextech.erp.model.Productrawmaterialassociation;

@Repository
@Transactional
public class ProductRMAssoDaoImpl extends SuperDaoImpl<Productrawmaterialassociation> implements ProductRMAssoDao {

	@Autowired
	SessionFactory sessionFactory;
	Session session = null;
	Transaction tx = null;

	@Override
	public Productrawmaterialassociation getPRMAssociationByPidRmid(long pid, long rmid) throws Exception{
 		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Productrawmaterialassociation.class);
		criteria.add(Restrictions.eq("isactive", true));
		criteria.add(Restrictions.eq("product.id", pid));
		criteria.add(Restrictions.eq("rawmaterial.id",rmid));
		Productrawmaterialassociation Productrawmaterialassociation = criteria.list().size() > 0 ? (Productrawmaterialassociation) criteria.list().get(0) : null;
		return Productrawmaterialassociation;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Productrawmaterialassociation> getProductRMAssoListByProductId(
			long productID) throws Exception {
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Productrawmaterialassociation.class);
		criteria.add(Restrictions.eq("product.id", productID));
		criteria.add(Restrictions.eq("isactive", true));
		return (List<Productrawmaterialassociation>) (criteria.list().size() > 0 ? criteria.list() : null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getProductList()
			throws Exception {
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Productrawmaterialassociation.class);
		
		criteria.setProjection(Projections.distinct(Projections.property("product.id")));
		criteria.add(Restrictions.eq("isactive", true));
		return (List<Long>) (criteria.list().size() > 0 ? criteria.list() : null);
	}
}

