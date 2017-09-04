package com.nextech.erp.daoImpl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.nextech.erp.dao.ProductRMAssoDao;
import com.nextech.erp.model.Productrawmaterialassociation;

@Repository

public class ProductRMAssoDaoImpl extends SuperDaoImpl<Productrawmaterialassociation> implements ProductRMAssoDao {

	@Autowired
	SessionFactory sessionFactory;
	Session session = null;
	Transaction tx = null;

	@Override
	public Productrawmaterialassociation getPRMAssociationByPidRmid(long pid, long rmid) throws Exception{
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Productrawmaterialassociation> criteria = builder.createQuery(Productrawmaterialassociation.class);
		Root<Productrawmaterialassociation> userRoot = (Root<Productrawmaterialassociation>) criteria.from(Productrawmaterialassociation.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("product"), pid),builder.equal(userRoot.get("rawmaterial"), rmid),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Productrawmaterialassociation> query = session.createQuery(criteria);
		  List<Productrawmaterialassociation> list = query.getResultList();
		  if (list.isEmpty()) {
		        return null;
		    }
		    return list.get(0);
	}

	@Override
	public List<Productrawmaterialassociation> getProductRMAssoListByProductId(
			long productID) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Productrawmaterialassociation> criteria = builder.createQuery(Productrawmaterialassociation.class);
		Root<Productrawmaterialassociation> userRoot  = (Root<Productrawmaterialassociation>) criteria.from(Productrawmaterialassociation.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("product"), productID),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Productrawmaterialassociation> query = session.createQuery(criteria);
		return query.getResultList();
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

	@Override
	public List<Productrawmaterialassociation> getProductRMListByProductId(long rmId)
			throws Exception {
		
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Productrawmaterialassociation> criteria = builder.createQuery(Productrawmaterialassociation.class);
		Root<Productrawmaterialassociation> userRoot  = (Root<Productrawmaterialassociation>) criteria.from(Productrawmaterialassociation.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("rawmaterial"), rmId),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Productrawmaterialassociation> query = session.createQuery(criteria);
		return query.getResultList();
	}

	@Override
	public Productrawmaterialassociation getProductRMListByProduct(
			long productId) throws Exception {
		
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Productrawmaterialassociation> criteria = builder.createQuery(Productrawmaterialassociation.class);
		Root<Productrawmaterialassociation> userRoot = (Root<Productrawmaterialassociation>) criteria.from(Productrawmaterialassociation.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("product"), productId),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Productrawmaterialassociation> query = session.createQuery(criteria);
		  List<Productrawmaterialassociation> list = query.getResultList();
		  if (list.isEmpty()) {
		        return null;
		    }
		    return list.get(0);
	}
}

