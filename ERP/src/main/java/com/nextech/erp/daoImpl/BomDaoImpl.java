package com.nextech.erp.daoImpl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nextech.erp.dao.BomDao;
import com.nextech.erp.model.Bom;
import com.nextech.erp.model.Productrawmaterialassociation;

@Repository

public class BomDaoImpl extends SuperDaoImpl<Bom> implements BomDao{

	@Override
	public List<Bom> getBomListByProductId(long productID) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Bom> criteria = builder.createQuery(Bom.class);
		Root<Bom> userRoot  = (Root<Bom>) criteria.from(Bom.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("product"), productID),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Bom> query = session.createQuery(criteria);
		return query.getResultList();
	}

	@Override
	public List<Bom> getBomListByProductIdAndBomId(long productId, long bomId)
			throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Bom> criteria = builder.createQuery(Bom.class);
		Root<Bom> userRoot  = (Root<Bom>) criteria.from(Bom.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("product"), productId),builder.equal(userRoot.get("id"), bomId),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Bom> query = session.createQuery(criteria);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getProductList() {
		session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Productrawmaterialassociation.class);
		criteria.setProjection(Projections.distinct(Projections.property("product.id")));
		criteria.add(Restrictions.eq("isactive", true));
		return (List<Long>) (criteria.list().size() > 0 ? criteria.list() : null);
		
	}

	@Override
	public Bom getBomByProductId(long productID) throws Exception {
		session = sessionFactory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Bom> criteria = builder.createQuery(Bom.class);
		Root<Bom> userRoot = (Root<Bom>) criteria.from(Bom.class);
		criteria.select(userRoot).where(builder.equal(userRoot.get("product"), productID),builder.equal(userRoot.get("isactive"), true));
		TypedQuery<Bom> query = session.createQuery(criteria);
		  List<Bom> list = query.getResultList();
		  if (list.isEmpty()) {
		        return null;
		    }
		    return list.get(0);
	}

}
